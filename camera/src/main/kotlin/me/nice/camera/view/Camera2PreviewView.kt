package me.nice.camera.view

import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.nice.camera.Camera2Helper

/**
 *
 */
class Camera2PreviewView(context: Context): ViewGroup(context) {

    private val tag = Camera2PreviewView::class.simpleName

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

    init {
        previewView.holder.addCallback(previewViewHolderCallback)
        camera2Helper.enumerateVideoCameras()
        camera2Helper.initDefaultCamera()
    }


    private fun createPreview(holder: SurfaceHolder) {
        camera2Helper.usingCameraInfo?.let { uCif ->
            val previewSize = getPreviewOutputSize(display = previewView.display,
                    supportSizes = uCif.sizes)
            previewView.setAspectRatio(previewSize.width, previewSize.height)
            previewView.post {
                lifecycleScope?.launch(Dispatchers.Main) {
                    val cameraDevice = camera2Helper.openCameraNew(cameraInfo = uCif)
                    val targets = mutableListOf<Surface>()
                    val previewSurface = previewView.holder.surface
                    targets.add(previewSurface)
//                            val recorderSurface = camera2Helper.
                    val captureSession = camera2Helper.createCaptureSession(device = cameraDevice,
                            targets = targets, cameraHandler)
//                            val captureRequest = camera2Helper.createCaptureRequest(captureSession = captureSession,
//                                    targets = targets)
                    val captureRequest = camera2Helper.createRecordCaptureRequest(captureSession = captureSession,
                            fpsRange = uCif.fpsRanges[0],
                            targets = targets)
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