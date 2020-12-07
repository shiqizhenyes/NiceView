package me.nice.camera

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.util.Log
import java.lang.Exception


class CameraHelper {

    private val tag = CameraHelper::class.simpleName

    var camera: Camera? = null
    var cameraInfoMap = mutableMapOf<Int, Camera.CameraInfo>()

    /**
     * check camera
     */
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }


    /**
     * openCamera
     */
    fun safeOpenCamera(cameraId: Int): Boolean {
        return try {
            camera = Camera.open(cameraId)
            true
        }catch (e: Exception) {
            false
        }
    }

    fun openBackCamera(): Boolean {
        var backCameraId: Int = -1
        cameraInfoMap.forEach {
            if (it.value.facing == CAMERA_FACING_BACK) {
                backCameraId = it.key
                return@forEach
            }
        }
        return if (backCameraId == -1) {
            false
        } else {
            return safeOpenCamera(backCameraId)
        }
    }


    fun openFrontCamera(): Boolean {
        var frontCameraId: Int = -1
        cameraInfoMap.forEach {
            if (it.value.facing == CAMERA_FACING_FRONT) {
                frontCameraId = it.key
                return@forEach
            }
        }
        return if (frontCameraId == -1) {
            false
        } else {
            return safeOpenCamera(frontCameraId)
        }
    }

    /**
     * 查询所有摄像头
     */
    fun initNumberOfCameras(): CameraHelper{
        val numberOfCameras = Camera.getNumberOfCameras()
        Log.d(tag, "numberOfCameras  $numberOfCameras")
        for (i in 0 until numberOfCameras) {
            val cameraInfo = Camera.CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
            cameraInfoMap[i] = cameraInfo
            Log.d(tag, "id $i cameraInfo  ${cameraInfoMap[i]}")
        }
        return this
    }


//    fun setCamera(camera: Camera?) {
//        this.camera = camera
//        camera?.let {
//
//        }
//    }
    
}