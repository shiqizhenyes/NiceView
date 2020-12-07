package me.nice.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.hardware.camera2.params.OutputConfiguration
import android.hardware.camera2.params.SessionConfiguration
import android.hardware.camera2.params.SessionConfiguration.SESSION_HIGH_SPEED
import android.hardware.camera2.params.SessionConfiguration.SESSION_REGULAR
import android.media.MediaRecorder
import android.os.Handler
import android.util.Log
import android.util.Range
import android.util.Size
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.camera2.internal.compat.CameraDeviceCompat.SESSION_OPERATION_MODE_CONSTRAINED_HIGH_SPEED
import androidx.camera.camera2.internal.compat.workaround.AeFpsRange
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jetbrains.annotations.NotNull
import java.util.logging.LogRecord
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Camera2Helper(private val context: Context) {

    private val tag = Camera2Helper::class.simpleName

    private var usingCameraId: String? = null
    var usingCameraInfo: CameraInfo? = null

//    private val recorder: MediaRecorder by lazy {
//
//    }
//
//    private createrRecorder() = MediaR
//
//
//    private val holder = (surfaceView).holder.apply {
//        addCallback(this@Camera2Helper)
//    }

//    private val holder = (surfaceView as AutoFitSurfaceView).holder.apply {
//        addCallback(this@Camera2Helper)
//    }

    private val cameraManager: CameraManager by lazy {
        context.applicationContext.getSystemService(Context.CAMERA_SERVICE)
                as CameraManager
    }

    /**
     * 获取相机参数
     */
    val characteristics: CameraCharacteristics by lazy {
        cameraManager.getCameraCharacteristics(usingCameraId!!)
    }

    /**
     * 枚举所有相机
     */
    internal fun enumerateVideoCameras(): List<CameraInfo> {
        val availableCameras: MutableList<CameraInfo> = mutableListOf()
        cameraManager.cameraIdList.forEach {id ->
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(id)
            val orientationInt = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING)!!
            val capabilities = cameraCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)!!
            val cameraConfig = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)!!
            val fpsRanges = cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)!!
            val physicalCameraIds = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                cameraCharacteristics.physicalCameraIds
            } else {
                null
            }
            var supportSize: Array<Size> = arrayOf()
            if (capabilities.contains(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE)) {
                supportSize = cameraConfig.getOutputSizes(MediaRecorder::class.java)
            }
            availableCameras.add(CameraInfo(getCameraNameString(orientationInt),
                    cameraId = id, sizes = supportSize, fpsRanges = fpsRanges))
        }
        return availableCameras
    }

    private fun getCameraNameString(orientation: Int): String {
        return when(orientation) {
            CameraCharacteristics.LENS_FACING_FRONT -> {
                "FRONT"
            }
            CameraCharacteristics.LENS_FACING_BACK -> {
                "BACK"
            }
            CameraCharacteristics.LENS_FACING_EXTERNAL -> {
                "EXTERNAL"
            }
            else -> {
                "UNKNOWN"
            }
        }
    }

    /**
     * 初始化默认相机
     */
    fun initDefaultCamera() {
        enumerateVideoCameras().forEach { cameraInfo ->
            if (cameraInfo.name == "BACK") {
                usingCameraId = cameraInfo.cameraId
                usingCameraInfo = cameraInfo
                return@forEach
            }
        }
    }


    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)

    private val cameraPermissionRequestCode: Int = 0x9999


    /**
     * 开启相机
     * ActivityCompat.requestPermissions(context, permissions, cameraPermissionRequestCode)
     */
    internal suspend fun openCamera(cameraInfo: CameraInfo, handler: Handler):
            CameraDevice = suspendCancellableCoroutine { cCont ->
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
                cameraManager.openCamera(
                    cameraInfo.cameraId, object : CameraDevice.StateCallback() {
                        override fun onOpened(camera: CameraDevice) = cCont.resume(camera)

                        override fun onDisconnected(camera: CameraDevice) {
                            Log.w(tag, "Camera ${cameraInfo.cameraId} has been disconnected")
                            if (context is Activity) {
                                context.finish()
                            }
                            if (context is Fragment) {
                                context.activity?.finish()
                            }
                        }

                        override fun onError(camera: CameraDevice, error: Int) {
                            val msg = when (error) {
                                ERROR_CAMERA_DEVICE -> "Fatal (device)"
                                ERROR_CAMERA_DISABLED -> "Device policy"
                                ERROR_CAMERA_IN_USE -> "Camera in use"
                                ERROR_CAMERA_SERVICE -> "Fatal (service)"
                                ERROR_MAX_CAMERAS_IN_USE -> "Maximum cameras in use"
                                else -> "Unknown"
                            }
                            val exc = RuntimeException("Camera $usingCameraId error:($error) $msg")
                            Log.e(tag, exc.message, exc)
                            if (cCont.isActive) cCont.resumeWithException(exc)
                        }
                    },
                    handler
                )
            }
        }
    }

    /**
     * 开启相机
     */
    internal suspend fun openCameraNew(cameraInfo: CameraInfo):
            CameraDevice = suspendCancellableCoroutine { cCont ->
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                cameraManager.openCamera(cameraInfo.cameraId, context.mainExecutor,
                    object : CameraDevice.StateCallback() {
                        override fun onOpened(camera: CameraDevice) = cCont.resume(camera)
                        override fun onDisconnected(camera: CameraDevice) {
                            Log.w(tag, "Camera ${cameraInfo.cameraId} has been disconnected")
                            if (context is Activity) {
                                context.finish()
                            }
                            if (context is Fragment) {
                                context.activity?.finish()
                            }
                        }

                        override fun onError(camera: CameraDevice, error: Int) {
                            val msg = when (error) {
                                ERROR_CAMERA_DEVICE -> "Fatal (device)"
                                ERROR_CAMERA_DISABLED -> "Device policy"
                                ERROR_CAMERA_IN_USE -> "Camera in use"
                                ERROR_CAMERA_SERVICE -> "Fatal (service)"
                                ERROR_MAX_CAMERAS_IN_USE -> "Maximum cameras in use"
                                else -> "Unknown"
                            }
                            val exc = RuntimeException("Camera $usingCameraId error:($error) $msg")
                            Log.e(tag, exc.message, exc)
                            if (cCont.isActive) cCont.resumeWithException(exc)
                        }
                    })
            }
        }
    }

    /**
     * 创建采集会话
     * SESSION_REGULAR
     * SESSION_HIGH_SPEED
     */
    internal suspend fun createCaptureSession(device: CameraDevice,
                                             targets: List<Surface>,
                                             handler: Handler? =null): CameraCaptureSession
    = suspendCoroutine { continuation ->
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val targetsConfigurations = mutableListOf<OutputConfiguration>()
            targets.forEach { t ->
                targetsConfigurations.add(OutputConfiguration(t))
            }
            val sessionConfiguration = SessionConfiguration(SESSION_REGULAR,
                targetsConfigurations, context.mainExecutor,
                object : CameraCaptureSession.StateCallback() {

                    override fun onConfigured(session: CameraCaptureSession) = continuation.resume(session)

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        val exc = RuntimeException("Camera ${device.id} session configuration failed")
                        Log.e(tag, exc.message, exc)
                        continuation.resumeWithException(exc)
                    }
                })
            device.createCaptureSession(sessionConfiguration)
        } else {
            device.createCaptureSession(targets, object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(session: CameraCaptureSession) = continuation.resume(session)
                override fun onConfigureFailed(session: CameraCaptureSession) {
                    val exc = RuntimeException("Camera ${device.id} session configuration failed")
                    Log.e(tag, exc.message, exc)
                    continuation.resumeWithException(exc)
                }
            }, handler)
        }
    }


    /**
     * 创建捕捉请求
     */
    internal fun createCaptureRequest(captureSession: CameraCaptureSession, targets: List<Surface>): CaptureRequest {
        return captureSession.device.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
            targets.forEach { t ->
                addTarget(t)
            }
        }.build()
    }

    /**
     * 创建录制捕捉请求
     */
    internal fun createRecordCaptureRequest(captureSession: CameraCaptureSession,
                                            fpsRange: Range<Int>,
                                            targets: List<Surface>): CaptureRequest {
        return captureSession.device.createCaptureRequest(CameraDevice.TEMPLATE_RECORD).apply {
            targets.forEach { t ->
                addTarget(t)
                set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, fpsRange)
            }
        }.build()
    }


//    private fun initializeCamera() {
//        GlobalScope.launch {
//            usingCameraInfo?.let { openCameraNew(it) }
//        }
//    }

//    override fun surfaceCreated(holder: SurfaceHolder) {
//        Log.d(tag, "surfaceCreated")
//        initDefaultCamera()
//        val previewSize = getPreviewOutputSize(display =
//        (surfaceView as AutoFitSurfaceView).display,
//                characteristics = this.characteristics, SurfaceHolder::class.java)
//        surfaceView.setAspectRatio(previewSize.width, previewSize.height)
//        surfaceView.post {
//            initializeCamera()
//        }
//    }

//    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//    }
//
//    override fun surfaceDestroyed(holder: SurfaceHolder) {
//    }


}