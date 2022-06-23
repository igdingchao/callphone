package com.demo.phone

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.demo.mykotlinphone.R

class MainBean(
    var img_pic: Int = R.mipmap.ic_launcher,
    var imageUri: String = "",
    var picPath: String = "",
    var visible: Int = View.GONE,
    var et_phone: String = "",
) {

}

