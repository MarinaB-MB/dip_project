package com.deadely.it_lingua.ui.tests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.it_lingua.R
import com.deadely.it_lingua.model.Test
import kotlinx.android.synthetic.main.fragment_home.view.tvTitle
import kotlinx.android.synthetic.main.lesson_item.view.*

class TestsAdapter : RecyclerView.Adapter<TestsAdapter.TestsViewHolder>() {
    private val list: MutableList<Test> = mutableListOf()
    private var listener: OnItemClickListener? = null

    fun setOnClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    fun setData(newList: List<Test>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = TestsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
    )

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class TestsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(test: Test) {
            with(itemView) {
                if (position != 0 && !list[position - 1].isChecked) {
                    isEnabled = false
                }
                tvTitle.text = test.title
                if (test.isChecked) {
                    ivChecked.setImageResource(R.drawable.ic_baseline_check_24_green)
                } else {
                    ivChecked.setImageResource(R.drawable.ic_baseline_check_24_gray)
                }
                itemView.setOnClickListener { listener?.onItemClick(test) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(test: Test)
    }
}
