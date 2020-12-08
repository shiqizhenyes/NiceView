package me.nice.camera.view

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.nice.camera.Camera2Helper

/**
 *
 */
class Camera2PreviewView(context: Context): ViewGroup(context) {

    private val tag = Camera2PreviewView::class.simpleName

    private var recordVideoPreviewView: AutoFitSurfaceView = AutoFitSurfaceView(context)

    var lifecycleScope: LifecycleCoroutineScope? = null
        get() = field
        set(value) {
            field = value
        }

    private val cameraThread: HandlerThread = HandlerThread("cameraThread").apply {
        start()
    }

    private val cameraHandler = Handler(cameraThread.looper)

    init {
        recordVideoPreviewView.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
        )
        addView(recordVideoPreviewView)
    }

    /**
     * 开启预览
     */
    fun startPreview() {
        val camera2Helper = Camera2Helper(context)
//        camera2Helper.enumerateVideoCameras()
        recordVideoPreviewView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d(tag, "surfaceCreated")
                camera2Helper.initDefaultCamera()
                camera2Helper.usingCameraInfo?.let { cif ->
                    val previewSize = getPreviewOutputSize(display = recordVideoPreviewView.display,
                            supportSizes = cif.sizes)
                    recordVideoPreviewView.setAspectRatio(previewSize.width, previewSize.height)
                    recordVideoPreviewView.post {
                        lifecycleScope?.launch(Dispatchers.Main) {
                            val cameraDevice = camera2Helper.openCameraNew(cameraInfo = cif)
                            val targets = mutableListOf<Surface>()
                            val previewSurface = recordVideoPreviewView.holder.surface
                            targets.add(previewSurface)
//                            val recorderSurface = camera2Helper.
                            val captureSession = camera2Helper.createCaptureSession(device = cameraDevice,
                                    targets = targets, cameraHandler)
//                            val captureRequest = camera2Helper.createCaptureRequest(captureSession = captureSession,
//                                    targets = targets)
                            val captureRequest = camera2Helper.createRecordCaptureRequest(captureSession = captureSession,
                                    fpsRange = cif.fpsRanges[0],
                                    targets = targets)
                            captureSession.setRepeatingRequest(captureRequest,
                                    object : CameraCaptureSession.CaptureCallback() {
                                    }, cameraHandler)
                        }
                    }
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }

        })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }


}