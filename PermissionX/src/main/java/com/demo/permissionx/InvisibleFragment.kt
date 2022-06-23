//package com.demo.permissionx
//
//import android.content.pm.PackageManager
//import android.util.Log
//import androidx.fragment.app.Fragment
//import java.util.*
//import kotlin.collections.ArrayList
//
//typealias PermissionCallback = (Boolean, List<String>) -> Unit
//
////不重写oncreate就代表的是不可见的view，添加到activity里面就行了
//class InvisibleFragment : Fragment() {
//
//    private lateinit var callback: PermissionCallback
//
//    fun requestNow(cb: PermissionCallback,  permissions: Array<String>) {
//        callback = cb
//        requestPermissions(permissions, 1)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>, grantResults: IntArray
//    ) {
//        if (requestCode == 1) {
//            val deniedList = ArrayList<String>()
//            for ((index, result) in grantResults.withIndex()) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    deniedList.add(permissions[index])
//                }
//            }
//            val allGranted = deniedList.isEmpty()
//            //denied 拒绝，granted 允许
//            callback(allGranted, deniedList)
//        }
//    }
//}