package org.m0skit0.android.ikeachallenge.util.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import org.m0skit0.android.ikeachallenge.R

internal abstract class BaseDialogFragment : DialogFragment() {

    protected abstract val layout: Int
    protected abstract val viewModel: BaseViewModel

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    protected open fun observeViewModel() {
        with(viewModel) {
            isLoading.observe({ lifecycle }) { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
            error.observe({ lifecycle }) { error ->
                showError(error)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = run {
        val view = activity?.layoutInflater?.inflate(layout, null)?.apply { initialize() }
        AlertDialog.Builder(activity)
            .setView(view)
            .apply { set() }
            .create()
    }

    /**
     * Override to set more attributes to AlertDialog being created by this DialogFragment
     */
    protected open fun AlertDialog.Builder.set() {
        // Nothing to do
    }

    /**
     * Override to initialize from the created view
     */
    protected open fun View.initialize() {
        // Nothing to do
    }

    private fun showLoading() {
        findNavController().navigate(R.id.loadingDialogFragment)
    }

    private fun hideLoading() {
        findNavController().popBackStack()
    }

    protected open fun showError(error: Throwable) {
        val bundle =
            ErrorDialogFragment.bundle(error)
        findNavController().navigate(R.id.errorDialogFragment, bundle)
    }
}
