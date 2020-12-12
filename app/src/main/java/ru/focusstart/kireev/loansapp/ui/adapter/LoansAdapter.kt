package ru.focusstart.kireev.loansapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.loan_item.view.*
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.entity.StateLoan

class LoansAdapter : RecyclerView.Adapter<LoansAdapter.LoanViewHolder>() {
    var onLoanItemClickListener: OnLoanItemClickListener? = null

    var loans: List<Loan> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loan_item, parent, false)
        return LoanViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(loans[position])
    }

    override fun getItemCount(): Int = loans.size

    inner class LoanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDateOfIssue: TextView = itemView.textViewDateOfIssue
        private val textViewFirstAndLastName: TextView = itemView.textViewFirstAndLastName
        private val textViewAmount: TextView = itemView.textViewAmount
        private val textViewState: TextView = itemView.textViewState

        fun bind(loan: Loan) {
            textViewDateOfIssue.text = loan.date
            textViewFirstAndLastName.text = loan.fullName
            textViewAmount.text = loan.amount.toString()
            with(textViewState) {
                text = when (loan.state) {
                    StateLoan.APPROVED -> {
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.secondaryVariant))
                        itemView.resources.getString(R.string.approved)
                    }
                    StateLoan.REGISTERED -> {
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.gray))
                        itemView.resources.getString(R.string.registered)
                    }
                    StateLoan.REJECTED -> {
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.primary))
                        itemView.resources.getString(R.string.rejected)
                    }
                }
            }

            itemView.setOnClickListener {
                onLoanItemClickListener?.onLoanItemClick(adapterPosition)
            }
        }
    }

    interface OnLoanItemClickListener {
        fun onLoanItemClick(position: Int)
    }
}