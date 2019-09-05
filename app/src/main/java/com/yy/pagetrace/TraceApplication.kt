package com.yy.pagetrace

import android.app.Application
import com.yy.library.IGCPageActivityTrace

class TraceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val trace = IGCPageActivityTrace.getInstance(this)
        trace.pageTrace = { activity, fragment ->
            if (activity != null) {
                println("trace activity = " + activity::class.java.simpleName)
            }
            if (fragment != null) {
                println("trace fragment = " + fragment::class.java.simpleName)
            }
        }
    }
}