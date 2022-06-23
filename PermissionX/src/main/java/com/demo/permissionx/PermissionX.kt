//package com.demo.permissionx
//
//import androidx.fragment.app.FragmentActivity
//
//private const val TAG = "InvisibleFragment"
//
//object PermissionX {
//
//    fun request(
//        activity: FragmentActivity,  permissions: Array<String>, callback:
//        PermissionCallback
//    ) {
//        val fragmentManager = activity.supportFragmentManager
//        val existedFragment = fragmentManager.findFragmentByTag(TAG)
//        //判断传入的Activity参数中是否已经包含了指定TAG的 Fragment
//        val fragment = if (existedFragment != null) {
//            existedFragment as InvisibleFragment
//        } else {
//            val invisibleFragment = InvisibleFragment()
//            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
//            //添加到activity中，同时指定一个TAG。
//            // 注意，在添加结束后一定要调用commitNow()方法，而不能调用commit()方法，
//            // 因为commit()方法并不会立即执行添加操作，因而无法保证下一行代码执行时
//            invisibleFragment
//        }
//        fragment.requestNow(callback, permissions)
//    }
//}