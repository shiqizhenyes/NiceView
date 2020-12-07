package me.nice.camera

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup

class CameraPreview(context: Context, private val surfaceView: SurfaceView,
                    private val camera: Camera): ViewGroup(context), SurfaceHolder.Callback {

    private val tag = CameraPreview::class.simpleName

    var holder: SurfaceHolder = surfaceView.holder.apply {
        addCallback(this@CameraPreview)
//        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS) //auto if it need
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(tag, "surfaceCreated")
        camera.setPreviewDisplay(holder)
        Log.d(tag, "sw " + surfaceView.width + " sh " + surfaceView.height)
//        camera.parameters.supportedPreviewSizes

        camera.parameters.previewSize
        camera.startPreview()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(tag, "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d(tag, "surfaceDestroyed")
        camera.stopPreview()
    }


}