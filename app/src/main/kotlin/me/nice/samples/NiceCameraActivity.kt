package me.nice.samples

import android.hardware.camera2.CameraCaptureSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import android.widget.TextView
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

class NiceCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNiceCameraBinding
    private lateinit var ncCamera2PreviewView: Camera2PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNiceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ncCamera2PreviewView = Camera2PreviewView(this)
        ncCamera2PreviewView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.root.addView(ncCamera2PreviewView)
        ncCamera2PreviewView.lifecycleScope = lifecycleScope
        ncCamera2PreviewView.startPreview()
    }

    override fun onResume() {
        super.onResume()
//        ncCamera2PreviewView.
    }


    override fun onDestroy() {
        super.onDestroy()
        ncCamera2PreviewView.stopPreview()
    }



}