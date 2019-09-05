package com.yy.pagetrace

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.yy.pagetrace.fragment.AFragment
import com.yy.pagetrace.fragment.BFragment
import com.yy.pagetrace.fragment.CFragment

/**
 * Main fragment provider
 *
 * @author yy
 */

const val TAB_HOME = 1
const val TAB_STUDY = 2
const val TAB_MINE = 3

class FragmentProvider(context: AppCompatActivity) {

    private val fragmentManager: FragmentManager = context.supportFragmentManager

    private var homeFragment: Fragment? = null

    private var studyFragment: Fragment? = null

    private var mineFragment: Fragment? = null


    fun switchFragment(tabId: Int, resId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        //隐藏 所有页面 解决Fragment重叠问题
        hideAllFragment(fragmentTransaction)
        when (tabId) {
            TAB_HOME -> if (homeFragment == null) {
                homeFragment = AFragment.newInstance("", "")
                fragmentTransaction.add(resId, homeFragment!!, TAB_HOME.toString())
            } else {
                fragmentTransaction.show(homeFragment!!)
            }
            TAB_STUDY -> {
                if (studyFragment == null) {
                    studyFragment = BFragment.newInstance("", "")
                    fragmentTransaction.add(resId, studyFragment!!, TAB_STUDY.toString())
                } else {
                    fragmentTransaction.show(studyFragment!!)
                }
            }
            TAB_MINE -> if (mineFragment == null) {
                mineFragment = CFragment.newInstance("", "")
                fragmentTransaction.add(resId, mineFragment!!, TAB_MINE.toString())
            } else {
                fragmentTransaction.show(mineFragment!!)
            }
            else -> {
            }
        }

        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有Fragment
     */
    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment!!)
        }
        if (studyFragment != null) {
            fragmentTransaction.hide(studyFragment!!)
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment!!)
        }
    }

}
