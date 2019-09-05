package com.yy.library

import android.app.Activity
import android.app.Application
import androidx.annotation.Keep
import androidx.fragment.app.Fragment


/**
 * 由于依赖了router，所以要在组件初始化后再调用
 */
class IGCPageActivityTrace {

    var pageTrace: ((activity: Activity?, fragment: Fragment?) -> Unit)? = null
        set(value) {
            field = value
            value?.let { IGCPageFragmentTrace.pageTrace = it }
        }

    companion object {
        @Volatile
        private var instance: IGCPageActivityTrace? = null

        fun getInstance(app: Application) =
                instance ?: synchronized(this) {
                    instance ?: IGCPageActivityTrace(app).also { instance = it }
                }
    }

    private constructor(app: Application) {
        // 监听activity的 resume
        IGCPageTraceHelper.registerActivityLifecycleCallbacks(
                application = app,
                resume = {
                    activityResume(it)
                })


    }

    /**
     * Track 页面浏览事件
     *
     * @param activity Activity
     */
    @Keep
    private fun activityResume(activity: Activity?) {
        activity ?: return

        try {
            val annotation = activity.javaClass.getAnnotation(TraceIgnore::class.java)

            if (annotation != null && annotation.fragments.isNotEmpty()) {
                // 处理fragment
                IGCPageFragmentTrace.fragmentNameScope.clear()
                IGCPageFragmentTrace.fragmentNameScope.addAll(annotation.fragments.toList())

            } else {
                // 处理activity
                traceActivityFlow(activity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun traceActivityFlow(activity: Activity) {
        pageTrace?.invoke(activity, null)
        // println("page trace = " + activity::class.java.simpleName)
    }

}