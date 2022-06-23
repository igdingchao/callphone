package com.demo.phone

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.demo.mykotlinphone.R

class MainBeanLiveData (
    private val observableField: ObservableField<MainBean> = ObservableField<MainBean>(),
    private val mainBean: MainBean = MainBean(
        img_pic = R.mipmap.ic_launcher,
        visible = View.GONE,
        et_phone = "",
        imageUri = "",
        picPath = ""
    )
) :BaseObservable(){

    init {
        observableField.set(mainBean)
    }

    fun getLlVisible(): Int {
        return observableField.get()!!.visible
    }

    fun setLlVisible(visible: Int) {
        observableField.get()!!.visible = visible
//        notifyPropertyChanged(visible)
        notifyChange()
    }

    fun getEtPhone(): String {
        Log.i("TAG", "getEtPhone: " + observableField.get()!!.et_phone)
        val get = observableField.get()!!
        return get.et_phone
    }

    fun setEtPhone(etPhone: String) {
        Log.i("TAG", "setEtPhone: " + etPhone)
        observableField.get()!!.et_phone = etPhone
        notifyChange()
    }
    fun getImagPic(): Int {
        return observableField.get()!!.img_pic
    }

    fun setImagPic(imagePic: Int) {
        observableField.get()!!.img_pic = imagePic
        notifyChange()
    }

   fun getImageUri(): String {
        return observableField.get()!!.imageUri
    }

    fun setImageUri(imageUri: String) {
        observableField.get()!!.imageUri = imageUri
        notifyChange()
    }

   fun getPicPath(): String {
        return observableField.get()!!.picPath
    }

    fun setPicPath(picPath: String) {
        Log.i("TAG", "setPicPath: "+picPath)
        observableField.get()!!.picPath = picPath
        notifyChange()
    }



}