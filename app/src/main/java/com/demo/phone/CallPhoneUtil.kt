package com.demo.phone

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

class CallPhoneUtil {

    companion object {
        @SuppressLint("StaticFieldLeak")
        val context: Context = MyApplication.context

        //不需要点击确定，直接打电话
        fun callPhone(phoneNumber: String) {
            val intent = Intent()
            intent.action = Intent.ACTION_CALL
            intent.data = Uri.parse("tel:$phoneNumber")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

//打开拨号界面，填充输入手机号码，让用户自主的选择
//        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }
}