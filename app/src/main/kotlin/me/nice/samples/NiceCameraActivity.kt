package me.nice.samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import me.nice.camera.view.Camera2PreviewView
import me.nice.samples.R
import me.nice.samples.databinding.ActivityMainBinding
import me.nice.samples.databinding.ActivityNiceCameraBinding

class NiceCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNiceCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNiceCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ncCamera2PreviewView = Camera2PreviewView(this)
        ncCamera2PreviewView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
//        ncCamera2PreviewView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        binding.root.addView(ncCamera2PreviewView)
        ncCamera2PreviewView.lifecycleScope = lifecycleScope
        ncCamera2PreviewView.startPreview()
    }
}