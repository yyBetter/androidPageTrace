package com.yy.library

import android.app.Activity
import androidx.annotation.Keep
import androidx.fragment.app.Fragment


/**
 * 由于依赖了router，所以要在组件初始化后再调用
 */

const val TYPE_RESUME = 1

const val TYPE_ON_HIDDEN_CHANGE = 2

object IGCPageFragmentTrace {

    /**
     * fragment status
     */
    private val fragmentStatusList = mutableListOf<IGCFragmentStatus>()

    /**
     * fragment
     */
    private val fragments = mutableListOf<Fragment>()

    /**
     * 当前activity 下fragment的范围
     */
    val fragmentNameScope = mutableListOf<String>()

    /**
     * fragment的监听
     */
    var pageTrace: ((activity: Activity?, fragment: Fragment?) -> Unit)? = null


    /**
     * 监听f fragment hidden
     */
    fun addOnHiddenChange(fragment: Fragment, hidden: Boolean) {
        getFragmentStatus(fragment).hidden = hidden
        report(null, TYPE_ON_HIDDEN_CHANGE)
    }

    /**
     * 监听 fragment onResume
     */
    fun addOnResume(fragment: Fragment) {
        getFragmentStatus(fragment)
        report(fragment, TYPE_RESUME)
    }

    /**
     * 上报
     */
    private fun report(fragment: Fragment? = null, type: Int) {
        // 根据规则发现，当hidden不为空 不是true的 就是当前事物中被show的。
        val temp = scopeResult().firstOrNull { it.hidden == null || !it.hidden!! }
        temp ?: return

        when (type) {
            TYPE_RESUME -> {
                fragment ?: return
                // 避免多个fragment 同时执行resume
                if (temp.className == fragment::class.java.simpleName) {
                    val fragment = fragments.firstOrNull { it::class.java.simpleName == temp.className }
                    pageTrace?.invoke(null, fragment)
                    // println("当前展示的是 TYPE_RESUME = " + temp.className)
                }
            }
            TYPE_ON_HIDDEN_CHANGE -> {

                val fragment = fragments.firstOrNull { it::class.java.simpleName == temp.className }
                pageTrace?.invoke(null, fragment)

                // println("当前展示的是 TYPE_ON_HIDDEN_CHANGE = " + temp.className)
            }
            else -> {
                // pass
            }
        }
    }

    /**
     * 从所有fragments中找到当前activity内包含的那几个。
     */
    private fun scopeResult(): MutableList<IGCFragmentStatus> {
        val fragmentScopes = mutableListOf<IGCFragmentStatus>()
        fragmentStatusList.forEach {
            if (it.className in fragmentNameScope) {
                fragmentScopes.add(it)
            }
        }
        return fragmentScopes
    }

    /**
     * 获取当前fragment
     *
     * 没有创建，有则返回
     */
    private fun getFragmentStatus(fragment: Fragment): IGCFragmentStatus {
        var fragmentStatus = fragmentStatusList.firstOrNull { fragment::class.java.simpleName == it.className }

        if (fragmentStatus == null) {
            fragmentStatus = IGCFragmentStatus(className = fragment::class.java.simpleName)
            fragmentStatusList.add(fragmentStatus)
            fragments.add(fragment)
        }

        return fragmentStatus
    }


}

@Keep
data class IGCFragmentStatus(
        // 类名
        var className: String? = null,
        // 是否隐藏
        var hidden: Boolean? = null
)

