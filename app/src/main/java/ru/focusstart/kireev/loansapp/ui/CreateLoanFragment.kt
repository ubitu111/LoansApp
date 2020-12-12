package ru.focusstart.kireev.loansapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.create_loan_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.presentation.extension.ViewsExtensions.makeSnackbar
import ru.focusstart.kireev.loansapp.presentation.viewmodel.CreateLoanViewModel

class CreateLoanFragment : Fragment(R.layout.create_loan_fragment) {

    companion object {
        private const val BUNDLE_SLIDER_VALUE = "sliderValue"
    }

    private val viewModel by viewModel<CreateLoanViewModel>()
    private var sliderValue = 0.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.loadLoanConditions()
        } else {
            sliderValue = savedInstanceState.getFloat(BUNDLE_SLIDER_VALUE)
        }

        init()
    }

    private fun init() {

        viewModel.loading.observe(viewLifecycleOwner, {
            progressBarCreateLoan.visibility =
                if (it) View.VISIBLE
                else View.INVISIBLE
        })

        viewModel.loanConditions.observe(viewLifecycleOwner, {
            handleConditions(it)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            handleError(it)
        })

        viewModel.result.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_createLoanFragment_to_successLoanCreatedInfoFragment)
        })

        buttonCreateLoan.setOnClickListener { createLoan() }


    }

    private fun handleConditions(loanConditions: LoanConditions) {
        with(loanConditions) {
            textViewPercent.text = percent.toString()
            textViewPeriod.text = period.toString()
            sliderMaxAmount.valueTo = maxAmount.toFloat()
            sliderMaxAmount.addOnChangeListener { _, value, _ ->
                textViewMaxAmount.text = value.toString()
            }

            sliderMaxAmount.value =
                if (sliderValue == 0.0f) {
                    textViewMaxAmount.text = maxAmount.toString()
                    maxAmount.toFloat()
                } else {
                    textViewMaxAmount.text = sliderValue.toString()
                    sliderValue
                }
        }
    }

    private fun handleError(errorId: Int) {
        createLoanCoordinatorLayout.makeSnackbar(getString(errorId))
    }

    private fun createLoan() {
        if (editTextLastName.text?.isNotEmpty() == true
            && editTextFirstName.text?.isNotEmpty() == true
            && editTextPhoneNumber.text?.isNotEmpty() == true
        ) {
            viewModel.createLoan(
                LoanRequest(
                    textViewMaxAmount.text.toString().toFloat().toInt(),
                    editTextFirstName.text.toString(),
                    editTextLastName.text.toString(),
                    textViewPercent.text.toString().toDouble(),
                    textViewPeriod.text.toString().toInt(),
                    editTextPhoneNumber.text.toString()
                )
            )
        } else {
            createLoanCoordinatorLayout.makeSnackbar(getString(R.string.fill_all_fields))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (sliderMaxAmount != null) {
            outState.putFloat(BUNDLE_SLIDER_VALUE, sliderMaxAmount.value)
        }
        super.onSaveInstanceState(outState)
    }
}