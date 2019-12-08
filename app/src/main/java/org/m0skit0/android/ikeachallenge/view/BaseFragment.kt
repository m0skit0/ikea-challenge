package org.m0skit0.android.ikeachallenge.view

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.m0skit0.android.ikeachallenge.R

internal abstract class BaseFragment : Fragment() {

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

    private fun showLoading() {
        findNavController().navigate(R.id.loadingDialogFragment)
    }

    private fun hideLoading() {
        findNavController().popBackStack()
    }

    protected open fun showError(error: Throwable) {
        val bundle = ErrorDialogFragment.bundle(error)
        findNavController().navigate(R.id.errorDialogFragment, bundle)
    }
}