package org.m0skit0.android.ikeachallenge.util.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.m0skit0.android.ikeachallenge.R

internal class LoadingDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = run {
        dialog?.setCanceledOnTouchOutside(false)
        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = run {
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_loading, null)
        AlertDialog.Builder(activity)
            .setView(view)
            .setCancelable(false)
            .create()
    }
}
