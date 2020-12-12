package ru.focusstart.kireev.loansapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.list_loans_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.presentation.extension.ViewsExtensions.makeSnackbar
import ru.focusstart.kireev.loansapp.presentation.viewmodel.ListLoansViewModel
import ru.focusstart.kireev.loansapp.ui.adapter.LoansAdapter

class ListLoansFragment : Fragment(R.layout.list_loans_fragment),
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel by viewModel<ListLoansViewModel>()
    private val recyclerViewAdapter = LoansAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun createLoan() {
        findNavController().navigate(R.id.action_listLoansFragment_to_createLoanFragment)
    }

    private fun init() {
        swipeRefreshListLoans.setOnRefreshListener(this)

        buttonAddNewLoan.setOnClickListener { createLoan() }

        recyclerViewListLoans.adapter = recyclerViewAdapter

        createOnLoanClickListener()
        createRecyclerViewOnScrollListener()

        viewModel.loading.observe(viewLifecycleOwner, {
            changeUiIsLoading(it)
        })

        viewModel.allLoans.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                listLoanCoordinatorLayout.makeSnackbar(getString(R.string.no_loans_yet))
            }
            recyclerViewAdapter.loans = it
        })

        viewModel.error.observe(viewLifecycleOwner, {
            handleError(it)
        })

        viewModel.loadLoans()
    }

    private fun createRecyclerViewOnScrollListener() {
        recyclerViewListLoans.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && buttonAddNewLoan.isVisible) {
                    buttonAddNewLoan.hide()
                } else if (dy < 0 && !buttonAddNewLoan.isVisible) {
                    buttonAddNewLoan.show()
                }
            }
        })
    }

    private fun createOnLoanClickListener() {
        recyclerViewAdapter.onLoanItemClickListener =
            object : LoansAdapter.OnLoanItemClickListener {
                override fun onLoanItemClick(position: Int) {
                    val loanId = recyclerViewAdapter.loans[position].id
                    val action =
                        ListLoansFragmentDirections
                            .actionListLoansFragmentToDetailLoanInfoFragment(
                                loanId
                            )
                    findNavController().navigate(action)
                }
            }
    }

    private fun handleError(errorId: Int) {
        listLoanCoordinatorLayout.makeSnackbar(getString(errorId))
    }

    private fun changeUiIsLoading(loading: Boolean) {
        swipeRefreshListLoans.isRefreshing = loading
    }

    override fun onRefresh() {
        viewModel.loadLoans()
    }
}