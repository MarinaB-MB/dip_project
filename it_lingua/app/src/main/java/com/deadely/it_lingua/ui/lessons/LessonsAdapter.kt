package com.deadely.it_lingua.ui.lessons

import android.os.Build
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.Html.fromHtml
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.it_lingua.R
import com.deadely.it_lingua.model.Lesson
import com.deadely.it_lingua.ui.lessons.LessonsAdapter.LessonsViewHolder
import kotlinx.android.synthetic.main.lesson_item.view.*

class LessonsAdapter : RecyclerView.Adapter<LessonsViewHolder>() {
    private val list: MutableList<Lesson> = mutableListOf()
    private var listener: OnItemClickListener? = null

    fun setOnClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    fun setData(newList: List<Lesson>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LessonsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
    )

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class LessonsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(lesson: Lesson) {
            with(itemView) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvTitle.text = fromHtml(
                        lesson.title,
                        FROM_HTML_MODE_COMPACT
                    )
                } else {
                    tvTitle.text = fromHtml(lesson.title)
                }
                if (lesson.isChecked) {
                    ivChecked.setImageResource(R.drawable.ic_baseline_check_24_green)
                } else {
                    ivChecked.setImageResource(R.drawable.ic_baseline_check_24_gray)
                }
                itemView.setOnClickListener { listener?.onItemClick(lesson) }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(lesson: Lesson)
    }
}
