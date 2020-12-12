package ru.focusstart.kireev.loansapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.detail_loan_info_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.entity.StateLoan
import ru.focusstart.kireev.loansapp.presentation.extension.ViewsExtensions.makeSnackbar
import ru.focusstart.kireev.loansapp.presentation.viewmodel.DetailLoanInfoViewModel

class DetailLoanInfoFragment : Fragment(R.layout.detail_loan_info_fragment) {

    private val viewModel by viewModel<DetailLoanInfoViewModel>()
    private val args: DetailLoanInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        viewModel.loan.observe(viewLifecycleOwner, {
            fillLoanInfo(it)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            layoutDetailInfo.makeSnackbar(getString(it))
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            detailInfoProgressBar.visibility =
                if (it) View.VISIBLE
                else View.INVISIBLE
        })

        val loanId = args.loanId
        viewModel.loadLoan(loanId)

        detailButtonOk.setOnClickListener { navigate() }
    }

    private fun fillLoanInfo(loan: Loan) {
        detailTextViewFullName.text = loan.fullName
        detailTextViewPhoneNumber.text = loan.phoneNumber
        detailTextViewAmount.text = loan.amount.toString()
        detailTextViewDate.text = loan.date
        detailTextViewPeriod.text = loan.period.toString()
        detailTextViewPercent.text = loan.percent.toString()
        when (loan.state) {
            StateLoan.APPROVED -> setImageAndText(
                R.drawable.approved,
                R.string.approved_loan_detail_info,
                R.color.secondaryVariant
            )
            StateLoan.REGISTERED -> setImageAndText(
                R.drawable.registered,
                R.string.registered_loan_detail_info,
                R.color.gray
            )
            StateLoan.REJECTED -> setImageAndText(
                R.drawable.rejected,
                R.string.rejected_loan__detail_info,
                R.color.primary
            )
        }
    }

    private fun setImageAndText(imageId: Int, textId: Int, textColorId: Int) {
        imageViewStateLoan.setImageResource(imageId)
        detailTextViewDescription.text = getString(textId)
        detailTextViewDescription.setTextColor(ContextCompat.getColor(requireContext(), textColorId))
    }

    private fun navigate() {
        findNavController().navigate(R.id.action_detailLoanInfoFragment_to_listLoansFragment)
    }

}