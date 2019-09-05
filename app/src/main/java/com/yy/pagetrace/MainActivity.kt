package com.yy.pagetrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yy.library.TraceIgnore
import kotlinx.android.synthetic.main.activity_main.*

@TraceIgnore(fragments = ["AFragment", "BFragment", "CFragment"])
class MainActivity : AppCompatActivity() {

    private lateinit var fragmentProvider: FragmentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentProvider = FragmentProvider(this)

        listener()
    }

    private fun listener() {

        bottomNavigationView.setOnClickListener {
            fragmentProvider.switchFragment(TAB_HOME, R.id.container)
        }
        liveStartTip.setOnClickListener {
            fragmentProvider.switchFragment(TAB_STUDY, R.id.container)
        }
        tipLayout.setOnClickListener {
            fragmentProvider.switchFragment(TAB_MINE, R.id.container)
        }

    }
}
