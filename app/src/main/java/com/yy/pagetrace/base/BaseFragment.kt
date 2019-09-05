package com.yy.pagetrace.base

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.yy.library.IGCPageFragmentTrace

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        IGCPageFragmentTrace.addOnResume(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        IGCPageFragmentTrace.addOnHiddenChange(this, hidden)
    }

}
