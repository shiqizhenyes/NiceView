package me.nice.camera

import android.media.MediaCodec
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.annotation.RequiresApi
import java.io.File

@RequiresApi(Build.VERSION_CODES.M)
open class MediaRecorderHelper {

    private val tag = MediaRecorderHelper::class.simpleName

    companion object {
        private const val RECORDER_VIDEO_BITRATE: Int = 10_000_000
    }

    private val mediaRecorder  = MediaRecorder()

    val recorderSurface: Surface by lazy {
        val surface = MediaCodec.createPersistentInputSurface()
//        val surface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            MediaCodec.createPersistentInputSurface()
//        } else {
//            null
//        }
        surface
    }

    private lateinit var recordFile : File

    fun createRecordFile(recordPath: String) {
        this.recordFile = File(recordPath)
    }

    fun createRecordFile(recordFile: File) {
        this.recordFile = recordFile
        Log.d(tag, "recordFile  ${recordFile.absolutePath}")
    }

    var fps : Int = 30
    var videoSize: Size? = null

    /**
     *
     */
    fun prepareRecordVideo(videoSize: Size, fps: Int = 30) {
        this.videoSize = videoSize
        this.fps = fps
        mediaRecorder.apply {
            setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setOutputFile(recordFile)
            } else {
                setOutputFile(recordFile.absolutePath)
            }
            setVideoEncodingBitRate(RECORDER_VIDEO_BITRATE)
            if (fps > 0) {
                setVideoFrameRate(fps)
            }
            setVideoSize(videoSize.width, videoSize.height)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setInputSurface(recorderSurface)
            }
            prepare()
            release()
        }
    }

    fun startRecording() {
//        mediaRecorder.reset()
        mediaRecorder.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun resumeRecord() {
        mediaRecorder.resume()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun pauseRecord() {
        mediaRecorder.pause()
    }

    fun stopRecording() {
        mediaRecorder.reset()
        mediaRecorder.release()
        mediaRecorder.stop()
    }


}