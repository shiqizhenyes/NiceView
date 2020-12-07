package me.nice.camera

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class RecordVideoActivity : AppCompatActivity() {

    private lateinit var recordVideoPreviewView: SurfaceView

    private var fileName: String = ""
//    private var recorder: MediaRecorder? = null

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
        recordVideoPreviewView = SurfaceView(this)
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
            addView(recordebutton, ViewGroup.LayoutParams(
                    200, 200
            ))
            val buttonSet = ConstraintSet()
            buttonSet.connect(recordebutton.id, ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            buttonSet.connect(recordebutton.id, ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END,0);
            buttonSet.connect(recordebutton.id, ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START,0);
            buttonSet.constrainHeight(recordebutton.id, 200);
            buttonSet.applyTo(this)
        }
        setContentView(constraintLayout)

        val cameraHelper = CameraHelper()
        cameraHelper.initNumberOfCameras()
        if (cameraHelper.openFrontCamera()) {
            val preview = cameraHelper.camera?.let {
                CameraPreview(this, recordVideoPreviewView,
                    it
                )
            }
        }
    }

//    private lateinit var mediaRecorder: MediaRecorder

//    private fun initMediaRecorder() {
//        mediaRecorder = MediaRecorder().apply {
//            setVideoSource(MediaRecorder.VideoSource.CAMERA)
//            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
//            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
//            setPreviewDisplay(mSurfaceHolder.getSurface());
//            setVideoSource(MediaRecorder.VideoSource.SURFACE);  //视频源
//            setAudioSource(MediaRecorder.AudioSource.MIC);  //音频源
//            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  //视频输出格式
//            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);  //音频格式
//            setVideoEncodingBitRate(1 * 1024 * 512);  //设置帧频率，然后就清晰了
//            setOrientationHint(90);  //输出旋转90度，保持竖屏录制
//            setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);  //视频录制格式
//            setMaxDuration(mRecordMaxTime * 1000);  //设置录制时长
            //mMediaRecorder.setMaxFileSize(1024*1024*10);  //setMaxFileSize与setMaxDuration设置其一即可
//            setOutputFile(fileName)
//        }
//    }

    private fun startRecord() {}

    @SuppressLint("RestrictedApi")
    private fun stopRecord() {
    }

    override fun onDestroy() {
        super.onDestroy()
//        mediaRecorder.reset()
//        mediaRecorder.release()
    }

}