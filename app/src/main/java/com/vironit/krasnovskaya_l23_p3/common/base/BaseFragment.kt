package com.vironit.krasnovskaya_l23_p3.common.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.vironit.krasnovskaya_l23_p3.common.util.AlertDialogUtil
import com.vironit.krasnovskaya_l23_p3.common.util.hideKeyboard
import com.vironit.krasnovskaya_l23_p3.common.util.toast

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    fun toast(message: String) {
        requireContext().toast(message)
    }

    fun showAlertDialog(title: String, message: String): AlertDialogUtil {
        return AlertDialogUtil(title, message)
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
}