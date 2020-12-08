package me.nice.camera


import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CaptureRequest
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.nice.camera.view.AutoFitSurfaceView
import me.nice.camera.view.getPreviewOutputSize
import java.io.File

class Camera2RecordVideoActivity: AppCompatActivity() {

    private val tag = Camera2RecordVideoActivity::class.simpleName

    private lateinit var outFileName: String
    private lateinit var recordVideoPreviewView: AutoFitSurfaceView
    private lateinit var recordButton: RecordButton

    @SuppressLint("SetTextI18n")
    private inner class RecordButton(context: Context): AppCompatButton(context) {
        init {
            text = "recorde"
            setOnClickListener {
                if (text == "stop") {
                    text = "recorde"
//                    stopRecord()
                } else {
                    text = "stop"
//                    val videoFile = File(outFileName)
//                    if (videoFile.exists()) {
//                        videoFile.delete()
//                    }
//                    startRecord()
                }
            }
        }
    }

    private val cameraThread: HandlerThread = HandlerThread("cameraThread").apply {
        start()
    }

    private val cameraHandler = Handler(cameraThread.looper)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outFileName = "${externalCacheDir?.absolutePath}/recodeVideo.mp4"
        Log.d(RecordVideoActivity::class.simpleName, "filePath $outFileName")
        recordVideoPreviewView = AutoFitSurfaceView(this)
        recordVideoPreviewView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
        )
        recordVideoPreviewView.id = 200
        recordButton = RecordButton(this)
        recordButton.id = 100
        val constraintLayout = ConstraintLayout(this).apply {
            this.id = 300
            addView(recordVideoPreviewView)
            addView(recordButton)
            val buttonSet = ConstraintSet()
            buttonSet.connect(recordButton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM, 0)
            buttonSet.connect(recordButton.id, ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0)
            buttonSet.connect(recordButton.id, ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START,0)
            buttonSet.constrainHeight(recordButton.id, 200)
            buttonSet.constrainWidth(recordButton.id, ViewGroup.LayoutParams.MATCH_PARENT)
            buttonSet.applyTo(this)
        }
        setContentView(constraintLayout)

        val camera2Helper = Camera2Helper(this)
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
                        lifecycleScope.launch(Dispatchers.Main) {
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

}