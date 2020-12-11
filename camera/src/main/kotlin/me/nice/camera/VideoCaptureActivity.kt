package me.nice.camera

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File

class VideoCaptureActivity : AppCompatActivity() {
    private lateinit var recordVideoPreviewView: PreviewView

    private var fileName: String = ""

    @SuppressLint("SetTextI18n")
    private inner class Recordebutton(context: Context): AppCompatButton(context) {
        init {
            text = "recorde"
            setOnClickListener {
                if (text == "stop") {
                    text = "recorde"
                    stopRecord()
                } else {
                    text = "stop"
                    val videoFile = File(fileName)
                    if (videoFile.exists()) {
                        videoFile.delete()
                    }
                    startRecord()
                }
            }
        }
    }

    private lateinit var recordebutton: Recordebutton

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileName = "${externalCacheDir?.absolutePath}/recodeVideo.mp4"
        Log.d(RecordVideoActivity::class.simpleName, "filePath $fileName")
        recordVideoPreviewView = PreviewView(this)
        recordVideoPreviewView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
        )
        recordVideoPreviewView.id = 200
        recordebutton = Recordebutton(this)
        recordebutton.id = 100
        val constraintLayout = ConstraintLayout(this).apply {
            this.id = 300
            addView(recordVideoPreviewView)
            addView(recordebutton)
            val buttonSet = ConstraintSet()
            buttonSet.connect(recordebutton.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM, 0)
            buttonSet.connect(recordebutton.id, ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0)
            buttonSet.connect(recordebutton.id, ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START,0)
            buttonSet.constrainHeight(recordebutton.id, 200)
            buttonSet.constrainWidth(recordebutton.id, ViewGroup.LayoutParams.MATCH_PARENT)
            buttonSet.applyTo(this)
        }
        setContentView(constraintLayout)
        initCameraProvider()
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private fun initCameraProvider() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private lateinit var videoCapture: VideoCapture

    @SuppressLint("RestrictedApi")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
        preview.setSurfaceProvider(recordVideoPreviewView.surfaceProvider)
        videoCapture = VideoCapture.Builder()
                .setAudioRecordSource(MediaRecorder.AudioSource.MIC)
                .build()
        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, videoCapture)
    }

    @SuppressLint("RestrictedApi")
    private fun startRecord() {
        val outputFileOptions = VideoCapture.OutputFileOptions.Builder(File(fileName))
        videoCapture.startRecording(outputFileOptions.build(), ContextCompat.getMainExecutor(this),

                object: VideoCapture.OnVideoSavedCallback{
                    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                        Log.d("videoCapture zack", "" + outputFileResults.savedUri.toString())
                    }

                    override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                        Log.d("videoCapture zack", "" + message)
                    }

                })

    }

    @SuppressLint("RestrictedApi")
    private fun stopRecord() {
        videoCapture.stopRecording()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecord()
    }
}