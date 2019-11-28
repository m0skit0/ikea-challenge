package org.m0skit0.android.ikeachallenge.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import org.m0skit0.android.ikeachallenge.R

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}