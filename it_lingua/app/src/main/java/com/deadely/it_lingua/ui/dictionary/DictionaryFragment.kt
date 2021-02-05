package com.deadely.it_lingua.ui.dictionary

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.deadely.it_lingua.R
import com.deadely.it_lingua.base.BaseFragment
import com.deadely.it_lingua.databinding.FragmentDictionaryBinding
import com.deadely.it_lingua.model.Word
import com.deadely.it_lingua.utils.makeGone
import com.deadely.it_lingua.utils.makeVisible
import com.deadely.it_lingua.utils.setActivityTitle
import com.deadely.it_lingua.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class DictionaryFragment : BaseFragment(R.layout.fragment_dictionary), DictionaryView {
    @Inject
    lateinit var provider: Provider<DictionaryPresenter>
    private val presenter by moxyPresenter { provider.get() }
    private val wordsAdapter = WordsAdapter()
    private var isChecked = false
    private val viewBinding by viewBinding(
        FragmentDictionaryBinding::bind
    )
    private lateinit var favoriteMenuItem: MenuItem

    companion object {
        fun newInstance() = DictionaryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView() {
        setActivityTitle(R.string.title_dictionary)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_favorite -> {
                context?.let {
                    isChecked = !isChecked
                    if (isChecked) {
                        item.icon =
                            ContextCompat.getDrawable(it, R.drawable.ic_baseline_favorite_red)
                    } else {
                        item.icon =
                            ContextCompat.getDrawable(it, R.drawable.ic_baseline_favorite_white)
                    }
                    presenter.onFavoritesChanged(isChecked)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dictionary_menu, menu)
        val menuItemSearch = menu.findItem(R.id.mnu_search)
        favoriteMenuItem = menu.findItem(R.id.mnu_favorite)
        favoriteMenuItem.isVisible = false
        menuItemSearch.apply { setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_ALWAYS) }
        val searchView = menuItemSearch.actionView as SearchView
        menuItemSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                favoriteMenuItem.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                clearList()
                presenter.getApiWords()
                favoriteMenuItem.isVisible = true
                return true
            }
        })
        with(searchView) {
            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        presenter.searchQuery(p0)
                        viewBinding.rvWords.smoothScrollToPosition(0)
                        searchView.clearFocus()
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        return false
                    }
                }
            )
        }
    }

    override fun setListeners() {
        wordsAdapter.setOnClickListener(object : WordsAdapter.OnItemClickListener {
            override fun doFavorite(word: Word) {
                presenter.addFavorite(word)
            }

            override fun removeFromFavorite(word: Word) {
                presenter.deleteFavorites(word)
            }

            override fun onItemClick(word: Word) {
            }
        })
        viewBinding.rvWords.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordsAdapter
        }
    }

    override fun clearList() {
        wordsAdapter.setData(listOf())
    }

    override fun setWordList(list: List<Word>) {
        with(viewBinding) {
            if (list.isEmpty()) {
                ivEmptyList.makeVisible()
                rvWords.makeGone()
            } else {
                ivEmptyList.makeGone()
                rvWords.makeVisible()
            }
            wordsAdapter.setData(list)
        }
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            viewBinding.apply {
                pvLoad.makeVisible()
                rvWords.makeGone()
                ivEmptyList.makeGone()
            }
        } else {
            viewBinding.apply {
                pvLoad.makeGone()
                rvWords.makeVisible()
                ivEmptyList.makeGone()
                favoriteMenuItem.isVisible = true
            }
        }
    }

    override fun showError(error: String) {
        viewBinding.nsvDictionaryContainer.snack(error)
    }

    override fun onBackButtonPressed() {
        presenter.exit()
    }

    override fun exitPressed() {
        presenter.exit()
    }
}
