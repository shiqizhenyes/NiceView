package me.nice.camera


import android.util.Range
import android.util.Size

data class CameraInfo(
        val name: String,
        val cameraId: String,
        val sizes: Array<Size>,
        val fpsRanges: Array<Range<Int>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CameraInfo

        if (name != other.name) return false
        if (cameraId != other.cameraId) return false
        if (!sizes.contentEquals(other.sizes)) return false
        if (!fpsRanges.contentEquals(other.fpsRanges)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + cameraId.hashCode()
        result = 31 * result + sizes.contentHashCode()
        result = 31 * result + fpsRanges.contentHashCode()
        return result
    }
}
