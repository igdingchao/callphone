package permissionx

import androidx.fragment.app.FragmentActivity

private const val TAG = "PermissionFragment"

object PermissionX {

    fun request(activity: FragmentActivity, permissions: Array<String>, callBack: CallBack) {
        val supportFragmentManager = activity.supportFragmentManager
        val beginTransaction = supportFragmentManager
            .beginTransaction()
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(TAG)
        val fragment = if (findFragmentByTag != null) {
            findFragmentByTag as PermissionFragment
        } else {
            val permissionFragment = PermissionFragment()
            beginTransaction.add(permissionFragment, TAG).commitNow()
//如果是commit，是会被搁置的，当你活动中接下来的所有初始化代码执行完以后，才会去真正add
//如果是commitNow，先把碎片那一系列的生命周期操作执行了，让你的碎片真正被“激活”了，才会按顺序执行你activity中的余下代码。
            permissionFragment
        }
        fragment.requestPermission(callBack, permissions)
    }

}