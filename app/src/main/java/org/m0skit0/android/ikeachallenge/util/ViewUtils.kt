package org.m0skit0.android.ikeachallenge.util

import android.widget.Toast
import androidx.fragment.app.Fragment

internal fun Fragment.toast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
