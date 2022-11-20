package com.example.top_android_repositoriesbs_23.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.top_android_repositoriesbs_23.R


class RepositoriesFragment() : Fragment() {

    private val viewModel: RepositoryViewModel by activityViewModels()
    var isScrolling = false
    var isScrollingAPi = true
    var currentItems = 0
    var totalItems:Int = 0
    var scrollOutItems:Int = 0
    var pageNumber :Int= 1
    var qry = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repositories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.repositoriesRecyclerView)
        val adapter = RepositoriesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = recyclerView.layoutManager!!.getChildCount()
                totalItems = recyclerView.layoutManager!!.getItemCount()
                scrollOutItems = (recyclerView.layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()
                if (isScrolling && currentItems + scrollOutItems === totalItems) {
                    if (isScrollingAPi) {
                        isScrolling = false

                        pageNumber++
                        viewModel.searchRepositories(qry ,pageNumber)
                    }
                }
            }
        })

        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
        val totalCount: TextView = view.findViewById(R.id.countData)
        val errorView: View = view.findViewById(R.id.errorView)
        viewModel.status.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it == ApiStatus.LOADING) View.VISIBLE else View.GONE
            constraintLayout.visibility = if (it == ApiStatus.DONE) View.VISIBLE else View.GONE
            errorView.visibility = if (it == ApiStatus.ERROR) View.VISIBLE else View.GONE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as? SearchView
        searchView?.queryHint = "Search Repository 'Android'"
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                qry= query.toString()
                viewModel.searchRepositories(query ?: "",1)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "onQueryTextChange $newText")
                return true
            }

        })
    }
}