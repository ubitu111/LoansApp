package ru.focusstart.kireev.loansapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_success_loan_created_info.*
import ru.focusstart.kireev.loansapp.R

class SuccessLoanCreatedInfoFragment : Fragment(R.layout.fragment_success_loan_created_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonInfoOk.setOnClickListener { goToNextScreen() }
    }

    private fun goToNextScreen() {
        findNavController().navigate(R.id.action_successLoanCreatedInfoFragment_to_listLoansFragment)
    }
}