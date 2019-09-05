package com.yy.library

import android.app.Activity
import android.app.Application
import android.os.Bundle


const val SUFFIX = "igetcool://"

class IGCPageTraceHelper {

    companion object {

        fun registerActivityLifecycleCallbacks(application: Application, resume: (activity: Activity) -> Unit) {
            // activity 监听
            application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

                override
                fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {
                    // 跟踪activity
                    // traceAppViewScreen(activity)

                    resume(activity)
                }

                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityStopped(activity: Activity) {
                }

                override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {

                }

                override fun onActivityDestroyed(activity: Activity) {

                }
            })
        }
    }
}