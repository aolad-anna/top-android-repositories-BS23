package com.example.top_android_repositoriesbs_23.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.top_android_repositoriesbs_23.R
import java.text.SimpleDateFormat
import java.util.*


class RepositoryDetailFragment : Fragment() {

    private val viewModel: RepositoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_detail, container, false)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val avatarImageView: ImageView = view.findViewById(R.id.avatar)
        val repositoryNameTextView: TextView = view.findViewById(R.id.repositoryName)
        val ownerNameTextView: TextView = view.findViewById(R.id.ownerName)
        val repoDecTextView: TextView = view.findViewById(R.id.repoDescription)
        val watchersTextView: TextView = view.findViewById(R.id.watchersTextView)
        val forkTextView: TextView = view.findViewById(R.id.forkTextView)
        val branchTextView: TextView = view.findViewById(R.id.branchTextView)
        val languageTextView: TextView = view.findViewById(R.id.languageTextView)
        val updatedTextView: TextView = view.findViewById(R.id.updatedTextView)

        viewModel.selectedRepository?.let {
            avatarImageView.load(it.owner.avatar)
            repositoryNameTextView.text = it.name
            ownerNameTextView.text = "@${it.owner.login}"
            watchersTextView.text =  "Watchers: ${it.watchers}"
            repoDecTextView.text = it.description
            forkTextView.text =  "Forks: ${it.forks.toString()}"
            branchTextView.text = "Default Branch: ${it.defaultBranch}"
            languageTextView.text = "Language: ${it.language}"

            val date = it.updated_at
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            val newDate: Date = date?.let { it1 -> spf.parse(it1) } as Date
            spf = SimpleDateFormat("dd MMM yyyy")
            val newDateString: String = spf.format(newDate)

            updatedTextView.text = "Last Update: $newDateString"
        }
    }
}