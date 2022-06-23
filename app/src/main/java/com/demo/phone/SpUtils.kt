package com.demo.phone

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val FILE_NAME = "Default";
const val KEY = "notice_item"

object SpUtils {

    private val sharedPreferences: SharedPreferences =
        MyApplication.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var list: MutableList<PhoneNumberBean>
        get() {
            var mutableListOf = mutableListOf<PhoneNumberBean>()
            val json = sharedPreferences.getString(KEY, null);
            if (TextUtils.isEmpty(json)) {
                return mutableListOf;
            }
            mutableListOf =
                Gson().fromJson(json, object : TypeToken<MutableList<PhoneNumberBean>>() {}.type)
            //这里必须是object：  因为typetoken虽然是public类，但是构造函数是protected的
            return mutableListOf
        }
        set(value) {
            val json = Gson().toJson(value)
            editor.putString(KEY, json);
            editor.commit();
        }

    fun remove(index: Int) {
        var listTemp = mutableListOf<PhoneNumberBean>()
        var json = sharedPreferences.getString(KEY, null)
        if (TextUtils.isEmpty(json)) {
            return
        }
        listTemp = Gson().fromJson(json, object : TypeToken<MutableList<PhoneNumberBean>>() {}.type)
        listTemp.removeAt(index)
        json = Gson().toJson(listTemp)
        editor.putString(KEY, json)
        editor.commit()
    }
}