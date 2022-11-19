package com.example.top_android_repositoriesbs_23.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.top_android_repositoriesbs_23.R
import com.example.top_android_repositoriesbs_23.network.SearchRepositoriesResponse

class RepositoriesFragment() : Fragment() {

    private val viewModel: RepositoryViewModel by activityViewModels()


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
                viewModel.searchRepositories(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "onQueryTextChange $newText")
                return true
            }

        })
    }
}