package me.nice.camera.view

import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Log
import android.util.Range
import android.util.Size
import android.view.Surface
import android.view.SurfaceHolder
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.core.view.size
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.nice.camera.Camera2Helper
import me.nice.camera.MediaRecorderHelper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
class Camera2PreviewView(context: Context, attrs: AttributeSet? = null): ViewGroup(context, attrs) {

    private val tag = Camera2PreviewView::class.simpleName

    companion object {
        const val ERROR_UNKNOWN = 0
        const val ERROR_ENCODER = 1
        const val ERROR_MUXER = 2
        const val ERROR_RECORDING_IN_PROGRESS = 3
        const val ERROR_FILE_IO = 4
        const val ERROR_INVALID_CAMERA = 5
        const val CAPTURE_VIDEO = 6
        const val CAPTURE_PICTURE = 7
    }

    var captureMode = CAPTURE_PICTURE

    private val previewView: AutoFitSurfaceView = AutoFitSurfaceView(context)

    inner class PreviewViewHolderCallback: SurfaceHolder.Callback {

        override fun surfaceCreated(holder: SurfaceHolder) {
            Log.d(tag, "surfaceCreated")
            createPreview(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            Log.d(tag, "surfaceChanged format $format  width $width height $height")
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            Log.d(tag, "surfaceDestroyed")
            destroyPreview()
        }

    }

    private val previewViewHolderCallback = PreviewViewHolderCallback()

    private val camera2Helper = Camera2Helper(context)

    var lifecycleScope: LifecycleCoroutineScope? = null

    private val cameraThread: HandlerThread = HandlerThread("cameraThread").apply {
        start()
    }

    private val cameraHandler = Handler(cameraThread.looper)

    private lateinit var mediaRecorderHelper: MediaRecorderHelper
    private lateinit var mediaRecordSurface: Surface

    init {
        previewView.holder.addCallback(previewViewHolderCallback)
        camera2Helper.enumerateVideoCameras()
        camera2Helper.initDefaultCamera()
        if (captureMode == CAPTURE_VIDEO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaRecorderHelper = MediaRecorderHelper()
            }
            mediaRecordSurface = mediaRecorderHelper.recorderSurface
        }
    }


    private lateinit var previewSize: Size
    private lateinit var previewFps: Range<Int>

    private fun createPreview(holder: SurfaceHolder) {
        camera2Helper.usingCameraInfo?.let { uCif ->
            previewSize = getPreviewOutputSize(display = previewView.display,
                    supportSizes = uCif.sizes)
            previewFps = uCif.fpsRanges[0]
            previewView.setAspectRatio(previewSize.width, previewSize.height)
            previewView.post {
                lifecycleScope?.launch(Dispatchers.Main) {
                    val cameraDevice = camera2Helper.openCameraNew(cameraInfo = uCif)
                    val targets = mutableListOf<Surface>()
                    val previewSurface = previewView.holder.surface
                    targets.add(previewSurface)
                    val captureSession = camera2Helper.createCaptureSession(device = cameraDevice,
                            targets = targets, cameraHandler)
                    lateinit var captureRequest: CaptureRequest
                    captureRequest = if (captureMode == CAPTURE_PICTURE) {
                        camera2Helper.createCaptureRequest(captureSession = captureSession,
                                targets = targets)
                    } else {
//                        targets.add(mediaRecordSurface)
                        camera2Helper.createRecordCaptureRequest(captureSession = captureSession,
                                fpsRange = uCif.fpsRanges[0],
                                targets = targets)
                    }
                    captureSession.setRepeatingRequest(captureRequest,
                            object : CameraCaptureSession.CaptureCallback() {
                            }, cameraHandler)
                }
            }
        }
    }

    private fun destroyPreview() {
        camera2Helper.closeCamera()
        cameraThread.quitSafely()
    }

    /**
     * 开启预览
     */
    fun startPreview() {
        addView(previewView)
    }

    class OutputFileResults internal constructor(
            val savedUri: Uri?)

    @IntDef(ERROR_UNKNOWN,
            ERROR_ENCODER,
            ERROR_MUXER,
            ERROR_RECORDING_IN_PROGRESS,
            ERROR_FILE_IO,
            ERROR_INVALID_CAMERA)
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    annotation class VideoCaptureError

    interface OnVideoSavedCallback {

        fun onVideoSaved(outputFileResults: OutputFileResults)

        fun onError(@VideoCaptureError videoCaptureError: Int, message: String,
                    cause: Throwable?)
    }

    /**
     * 开始捕捉视频
     */
    fun startCapturingVideo() {
        captureMode = CAPTURE_VIDEO
//        stopPreview()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaRecorderHelper = MediaRecorderHelper()
            val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.CHINA)
            mediaRecorderHelper.createRecordFile(File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                    "REC_${sdf.format(Date())}.mp4"))
            mediaRecorderHelper.prepareRecordVideo(videoSize = previewSize,
                    fps = previewFps.upper)
            mediaRecordSurface = mediaRecorderHelper.recorderSurface
//            startPreview()
            mediaRecorderHelper.startRecording()
        }
    }

    /**
     * 停止捕捉视频
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun stopCaptureVideo() {
//        mediaRecorderHelper.stopRecording()
    }

    fun resumePreview() {}

    fun psusePreview() {
        cameraThread.quitSafely()
    }

    /**
     * 停止预览
     */
    fun stopPreview() {
        previewView.holder.removeCallback(previewViewHolderCallback)
        removeView(previewView)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        previewView.layout(l, t, r, b)
    }

}