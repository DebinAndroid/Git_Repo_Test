package com.test.gitrepo_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.gitrepo_test.database.DatabaseRepo

import com.test.gitrepo_test.databinding.FragmentFirstBinding
import com.test.gitrepo_test.util.LoadingState
import com.test.gitrepo_test.viewadapter.ReposAdapter
import com.test.gitrepo_test.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var viewModelAdapter: ReposAdapter? = null

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentFirstBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_first,container,false)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewmodel = viewModel
        viewModelAdapter = ReposAdapter()
        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        setupObserver()
        }


    private fun setupObserver() {
        viewModel.reposListResults.observe(viewLifecycleOwner, Observer<List<DatabaseRepo>> { repo ->
            repo?.apply {
                viewModelAdapter?.result = repo
            }
        })

        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> {
                    progressBar.visibility = View.VISIBLE
                    Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    // recycler_view.visibility = View.VISIBLE

                }
            }
        })
    }

}