package me.nice.camera

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.annotation.RequiresApi
import java.io.File

open class MediaRecorderHelper {

    private val tag = MediaRecorderHelper::class.simpleName

    companion object {
        private const val RECORDER_VIDEO_BITRATE: Int = 10_000_000
    }

    private val mediaRecorder  = MediaRecorder()


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

    val recorderSurface: Surface by lazy {
        val surface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MediaCodec.createPersistentInputSurface()
        } else {
            val videoEncoder = MediaCodec.createEncoderByType("video/avc")
            val videoFormat = MediaFormat.createVideoFormat("video/avc", this.videoSize!!.width,
                    this.videoSize!!.height)
            videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            videoFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000)
            videoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, this.fps)
            videoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5)
            // BITRATE_MODE_CBR 恒定码率
            videoFormat.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR)
            videoFormat.setInteger(MediaFormat.KEY_COMPLEXITY, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR)
            videoEncoder.configure(videoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            videoEncoder.createInputSurface()
        }
        surface
    }

    lateinit var inputSurface: Surface

    /**
     * 准备录制视频
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
            } else {}
            inputSurface = recorderSurface
//            release()
            prepare()
        }
    }

    fun startRecording() {
//        mediaRecorder.prepare()
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
//        mediaRecorder.reset()
//        mediaRecorder.release()
        mediaRecorder.stop()
    }


}