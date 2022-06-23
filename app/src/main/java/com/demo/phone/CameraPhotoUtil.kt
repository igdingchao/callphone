package com.demo.phone

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

const val TAKE_PHOTO: Int = 1//拍照
const val SCAN_OPEN_PHONE: Int = 2;// 相册

object CameraPhotoUtil {

    @RequiresApi(Build.VERSION_CODES.N)
     fun openCamera(context: Context, mainBeanLiveData: MainBeanLiveData): Uri {

        val outputImage = File(
            context.getExternalCacheDir(),
            SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"
        )

        Objects.requireNonNull(outputImage.getParentFile()).mkdirs();
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            var a = outputImage.createNewFile();
        } catch (e: Exception) {
        }
        var temp = if (Build.VERSION.SDK_INT >= 24) {
            //判断安卓的版本是否高于7.0，高于则调用高于的方法，低于则调用低于的方法
            //把文件转换成Uri对象
            /*
            因为android7.0以后直接使用本地真实路径是不安全的，会抛出异常。
            FileProvider是一种特殊的内容提供器，可以对数据进行保护
             */
            FileProvider.getUriForFile(
                context,
                "com.buildmaterialapplication.fileprovider", outputImage
            )
        } else {
            Uri.fromFile(outputImage)
        }
        mainBeanLiveData.setImageUri(temp.toString())
        //使用隐示的Intent，系统会找到与它对应的活动，即调用摄像头，并把它存储
        var intent0 = Intent("android.media.action.IMAGE_CAPTURE").putExtra(
            MediaStore.EXTRA_OUTPUT,
            temp
        );
        (context as Activity).startActivityForResult(intent0, TAKE_PHOTO);
        return temp
    }

     fun openGallery(context: Context) {
        (context as Activity).startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), SCAN_OPEN_PHONE
        )
    }
}