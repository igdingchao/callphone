package permissionx

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

const val REQUESTCODE = 1

class PermissionFragment : Fragment() {

    private val permissionMap = mapOf(
        Manifest.permission.CAMERA to "相机权限",
        Manifest.permission.READ_EXTERNAL_STORAGE to "读写权限",
        Manifest.permission.WRITE_EXTERNAL_STORAGE to "存储权限",
        Manifest.permission.CALL_PHONE to "电话权限"
    )

    private var denied = "" //代表被禁止的权限

    private lateinit var callBack: CallBack

    fun requestPermission(callBack: CallBack, permissions: Array<String>) {
        this.callBack = callBack
        requestPermissions(permissions, REQUESTCODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //granted 允许，denied 拒绝
//        requestCode: Int,请求的响应码
//        permissions: Array<out String>,请求的所有权限
//        grantResults: IntArray 请求的响应码的数组，[0,-1,-1] 元素为0代表允许，为-1代表不允许
        if (requestCode == REQUESTCODE) {
            if (denied.isEmpty()) {
                grantResults.forEachIndexed { index, result ->
                    if (result == PackageManager.PERMISSION_DENIED) {
                        permissionMap[permissions[index]]?.let {
                            denied += "$it "
                        }
                    }
                }
            }
            val allGrant = denied.isEmpty()//不包含权限字段，代表全部通过
            callBack.requestResult(allGrant, "请打开" + denied)
            denied = ""
        }
    }
}

interface CallBack {
    fun requestResult(allGranted: Boolean, denied: String)
}