package com.deadely.it_lingua.ui.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.it_lingua.R
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import kotlinx.android.synthetic.main.word_item.view.*

class WordsAdapter : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
    private val list: MutableList<Word> = mutableListOf()
    private var listener: OnItemClickListener? = null

    fun setOnClickListener(clickListener: OnItemClickListener) {
        listener = clickListener
    }

    fun setData(newList: List<Word>) {
        list.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = WordsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
    )

    override fun onBindViewHolder(holder: WordsAdapter.WordsViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    inner class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(word: Word) {
            with(itemView) {
                tvWord.text = word.text
                tvDescription.text = word.translate
                tvTr.text = word.transcription
                if (word.isFavorite) {
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_red)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_grey)
                }
                ivFavorite.setOnClickListener {
                    word.isFavorite = !word.isFavorite
                    if (word.isFavorite) {
                        ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_red)
                        listener?.doFavorite(word)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_grey)
                        listener?.removeFromFavorite(word)
                    }
                }
                itemView.setOnClickListener { listener?.onItemClick(word) }
                if (word == list.last()) vDivider.makeGone() else vDivider.makeVisible()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(word: Word)
        fun doFavorite(word: Word)
        fun removeFromFavorite(word: Word)
    }
}
