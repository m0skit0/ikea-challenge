package org.m0skit0.android.ikeachallenge.util.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.m0skit0.android.ikeachallenge.R

internal class ErrorDialogFragment : DialogFragment() {

    companion object {
        private const val KEY_ERROR = "error"
        fun bundle(throwable: Throwable): Bundle = bundleOf(KEY_ERROR to throwable)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = run {
        dialog?.setCanceledOnTouchOutside(false)
        super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = run {
        val error: Throwable = arguments?.get(KEY_ERROR) as Throwable? ?: Exception(getString(R.string.empty_error))
        AlertDialog.Builder(activity)
            .setTitle(getString(R.string.error))
            .setMessage("${error.javaClass.simpleName}: ${error.message}")
            .setPositiveButton(android.R.string.ok) { _, _ -> dismiss() }
            .create()
    }
}