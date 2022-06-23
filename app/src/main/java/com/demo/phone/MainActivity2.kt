package com.demo.phone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.demo.mykotlinphone.R
import com.demo.mykotlinphone.databinding.ActivityMain2Binding
import permissionx.CallBack
import permissionx.PermissionX
import kotlin.collections.ArrayList

private val items = arrayOf("拍照", "从相册中选择")

private val camera_read_permission = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

class MainActivity2 : AppCompatActivity() {
    lateinit var activityMain2Binding: ActivityMain2Binding

    lateinit var picPath: String

    lateinit var imageUri: String
    lateinit var phoneNumber: String
    lateinit var temp: Uri
    lateinit var mainBeanLiveData: MainBeanLiveData

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityMain2Binding =
            DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        activityMain2Binding!!.mainBeanLiveData = MainBeanLiveData()
        mainBeanLiveData = activityMain2Binding!!.mainBeanLiveData!!
        picPath = mainBeanLiveData.getPicPath()

        imageUri = mainBeanLiveData.getImageUri()

        val recyclerView = activityMain2Binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val mPhoneList = SpUtils.list
        val phoneAdapter = PhoneAdapter(this, mPhoneList)
        recyclerView.adapter = phoneAdapter

        findViewById<Button>(R.id.btn_add).setOnClickListener() {
            PermissionX.request(this, camera_read_permission, object : CallBack {
                override fun requestResult(allGranted: Boolean, denied: String) {
                    if (allGranted) {//同意
                        mainBeanLiveData.setLlVisible(View.VISIBLE)
                        choosePic()
                    } else {//拒绝
                        Toast.makeText(this@MainActivity2, denied, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        findViewById<Button>(R.id.btn_ok).setOnClickListener() {

            imageUri = mainBeanLiveData.getImageUri()
            if (TextUtils.isEmpty(imageUri) && TextUtils.isEmpty(picPath)) {
                Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }

            if (TextUtils.isEmpty(activityMain2Binding.etPhone.text)) {
                Toast.makeText(this, "请输入号码", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }

            mainBeanLiveData.setLlVisible(View.GONE)

            if (TextUtils.isEmpty(imageUri)) {
                mPhoneList.add(
                    PhoneNumberBean(
                        activityMain2Binding.etPhone.text.toString(),
                        "",
                        picPath
                    )
                )
            } else {
                mPhoneList.add(
                    PhoneNumberBean(
                        activityMain2Binding.etPhone.text.toString(),
                        imageUri,
                        ""
                    )
                )
            }
            phoneNumber = activityMain2Binding.etPhone.text.toString()
            SpUtils.list = mPhoneList
            phoneAdapter.notifyDataSetChanged();

            //把这对象保存到SP里面
            mainBeanLiveData.setImageUri("")
            mainBeanLiveData.setPicPath("")
            mainBeanLiveData.setEtPhone("")
            mainBeanLiveData.setImagPic(R.mipmap.ic_launcher)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun choosePic() {
        AlertDialog.Builder(this).setTitle("请选择图片")
            .setItems(items) { _, p1 ->
                if (p1 == 0) {
                    temp = CameraPhotoUtil.openCamera(this, mainBeanLiveData)
                } else {
                    CameraPhotoUtil.openGallery(this)
                }
            }.create().show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            TAKE_PHOTO -> {
                Glide.with(this).load(
                    BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(
                            temp
                        )
                    )
                ).into(activityMain2Binding!!.imgPic)
            }
            SCAN_OPEN_PHONE -> {
                if (resultCode == RESULT_OK) {
                    var selectImage: Uri? = intent?.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? = contentResolver.query(
                        selectImage!!,
                        filePathColumn, null, null, null
                    );
                    cursor?.moveToFirst();
                    //从数据视图中获取已选择图片的路径
                    val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                    picPath = cursor?.getString(columnIndex!!)!!;
                    cursor.close();
                    Log.i("TAG", "picPath: " + picPath)
                    mainBeanLiveData.setPicPath(picPath)
                }
            }
        }
    }
}