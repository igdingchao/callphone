package com.demo.phone

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.demo.mykotlinphone.R
import com.demo.mykotlinphone.databinding.ItemPhoneBinding
import permissionx.CallBack
import permissionx.PermissionX


class PhoneAdapter(
    val context: Context,
    val list: MutableList<PhoneNumberBean>
) : RecyclerView.Adapter<PhoneAdapter.MyViewHolder>() {

    private val phonePermission = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhoneAdapter.MyViewHolder {
        val inflate = DataBindingUtil.inflate<ItemPhoneBinding>(
            LayoutInflater.from(parent.context), R.layout.item_phone, parent, false
        )
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(
        holder: PhoneAdapter.MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val phoneNumberBean: PhoneNumberBean = list[position]
        holder.itemPhoneBinding.phoneNumberBean = phoneNumberBean
        holder.itemPhoneBinding.imgPhone.setOnClickListener {
            PermissionX.request(
                context as FragmentActivity,
                phonePermission,
                object : CallBack {
                    override fun requestResult(allGranted: Boolean, denied: String) {
                        if (allGranted) {//同意
                            CallPhoneUtil.callPhone(phoneNumber = phoneNumberBean.number)
                        } else {//拒绝
                            Toast.makeText(context, denied, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

        holder.itemPhoneBinding.imgPhone.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                AlertDialog.Builder(context).setTitle("是否删除？")
                    .setPositiveButton("是", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            list.removeAt(position)
                            SpUtils.remove(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, list.size - position)
                        }
                    }).setNegativeButton("否", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                        }
                    }).create().show()
                return false
            }
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val itemPhoneBinding: ItemPhoneBinding) :
        RecyclerView.ViewHolder(itemPhoneBinding.root) {
    }
}

