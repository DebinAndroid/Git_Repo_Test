package com.test.gitrepo_test.viewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.test.gitrepo_test.R
import com.test.gitrepo_test.database.DatabaseRepo
import com.test.gitrepo_test.databinding.RowReposBinding


class ReposAdapter() : RecyclerView.Adapter<ReposViewHolder>() {
    //  Repo LIst that our adapter will show
    var result: List<DatabaseRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val withDataBinding: RowReposBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        ReposViewHolder.LAYOUT,
        parent,
            false
        )
        return ReposViewHolder(withDataBinding)
    }

    override fun getItemCount() = result.size

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
    holder.viewDataBinding.also {
        it.results = result[position]
    }
    }
}

//viewholder for repo list
class ReposViewHolder(val viewDataBinding: RowReposBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.row_repos
    }
}
