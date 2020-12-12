package ru.focusstart.kireev.loansapp.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.entry_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.presentation.extension.ViewsExtensions.makeSnackbar
import ru.focusstart.kireev.loansapp.presentation.viewmodel.EntryViewModel

class EntryFragment : Fragment(R.layout.entry_fragment) {

    private val viewModel by viewModel<EntryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        registrationButton.setOnClickListener { registrationUser() }
        loginButton.setOnClickListener { login() }

        viewModel.loading.observe(viewLifecycleOwner, {
            changeUiIsLoading(it)
        })

        viewModel.successRegistration.observe(viewLifecycleOwner, {
            login()
        })

        viewModel.error.observe(viewLifecycleOwner, {
            handleError(it)
        })

        viewModel.successLogin.observe(viewLifecycleOwner, {
            navigateLoansList()
        })
    }

    private fun navigateLoansList() {
        findNavController().navigate(R.id.action_registrationFragment_to_listLoansFragment)
    }

    private fun registrationUser() {
        hideKeyboard()
        viewModel.registration(
            userLoginEditText.text.toString().trim(),
            userPasswordEditText.text.toString().trim()
        )
    }

    private fun login() {
        hideKeyboard()
        viewModel.login(
            userLoginEditText.text.toString().trim(),
            userPasswordEditText.text.toString().trim()
        )
    }

    private fun handleError(errorId: Int) {
        entryFragmentViewGroup.makeSnackbar(getString(errorId))
    }

    private fun changeUiIsLoading(loading: Boolean) {
        progressBar.visibility =
            if (loading) View.VISIBLE
            else View.INVISIBLE

        userLoginEditText.isClickable = !loading
        userPasswordEditText.isClickable = !loading
        loginButton.isClickable = !loading
        registrationButton.isClickable = !loading
    }

    private fun hideKeyboard() {
        userLoginEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        userPasswordEditText.onEditorAction(EditorInfo.IME_ACTION_DONE)
    }

}