package me.nice.samples

import android.hardware.camera2.CameraCaptureSession
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.nice.camera.Camera2Helper
import me.nice.camera.view.AutoFitSurfaceView
import me.nice.camera.view.Camera2PreviewView
import me.nice.camera.view.getPreviewOutputSize
import me.nice.samples.R
import me.nice.samples.databinding.ActivityMainBinding
import me.nice.samples.databinding.ActivityNiceCameraBinding
import net.qiujuer.genius.ui.widget.Button

class NiceCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNiceCameraBinding
    private var recording = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNiceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ncCamera2PreviewView.lifecycleScope = lifecycleScope
        binding.ncCamera2PreviewView.startPreview()
        binding.ncButton.setOnClickListener {
//            if (recording) {
//                recording = false
////                binding.ncButton.text = getText(R.string.recording)
//                binding.ncCamera2PreviewView.stopCaptureVideo()
//            } else {
//                recording = true
////                binding.ncButton.text = getText(R.string.stop)
//                binding.ncCamera2PreviewView.startCapturingVideo()
//            }
        }
    }

    override fun onResume() {
        super.onResume()
//        ncCamera2PreviewView.
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.ncCamera2PreviewView.stopPreview()
    }



}