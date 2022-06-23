package com.demo.phone

import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.demo.phone.MyApplication.Companion.context


object ImageViewAdapter {

    //加载本地图片
    @BindingAdapter("android:src")
    @JvmStatic
    fun setImage(imageView: ImageView, resId: Int) {
        imageView.setImageResource(resId)
    }

    //加载recycleView
    @BindingAdapter("recycleViewImage")
    @JvmStatic
    fun setImage(imageView: ImageView, phoneNumberBean: String) {
        Glide.with(context).load(phoneNumberBean).into(imageView)
    }

    //加载网络
    @BindingAdapter(value = ["image", "phone"], requireAll = true)
    @JvmStatic
    fun setImage(imageView: ImageView, resId: Int, phoneImage: String) {
        Log.i("TAG", "adapter.setImage:resId " + resId + "phoneImage" + phoneImage)
        if (TextUtils.isEmpty(phoneImage)) {
            Glide.with(context).load(resId).into(imageView)
        } else {
            Glide.with(context).load(phoneImage).into(imageView)
        }
    }

}