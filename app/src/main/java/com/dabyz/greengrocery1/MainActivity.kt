package com.dabyz.greengrocery1

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var imageView4Camera: ImageView? = null
    private val CAMERA_ACTIVITY_REQUEST_CODE = 1
    private var imageUri: Uri? = null
    var compressImg: ByteArray? = null

    fun openCamera(imageView4Camera: ImageView) {
        this.imageView4Camera = imageView4Camera
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, CAMERA_ACTIVITY_REQUEST_CODE)
    }

    private fun compressImg(uri: Uri): ByteArray {
        val stream = ByteArrayOutputStream()
        var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    compressImg = compressImg(result.uri)
                    imageView4Camera?.setImageURI(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e("Crop Activity Error", "Crop error: ${result.error}")
                }
            }
            CAMERA_ACTIVITY_REQUEST_CODE ->
                if (resultCode == Activity.RESULT_OK) imageUri?.let {
                    CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(300, 300).start(this)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
        supportFragmentManager.beginTransaction().apply {
            if (getSharedPreferences("dabyzPref", Context.MODE_PRIVATE).getString("mail", null) == null)
                replace(R.id.flFragment, SignUpFragment())
            else
                replace(R.id.flFragment, ProductsFragment())
            commit()
        }
    }

    private fun requestPermissions() {
        var permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .filterNot { ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
            .toTypedArray()
        if (permissions.isNotEmpty()) ActivityCompat.requestPermissions(this, permissions, 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.filterNot { it == PackageManager.PERMISSION_GRANTED }.isNotEmpty())
            exitProcess(0)
    }

}