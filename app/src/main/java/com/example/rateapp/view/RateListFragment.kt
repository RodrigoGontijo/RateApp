package com.example.rateapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.fragment_rate_list.*
import org.koin.android.viewmodel.ext.android.viewModel

import android.view.*
import androidx.navigation.Navigation
import com.example.rateapp.R
import com.example.rateapp.viewmodel.RateListViewModel


class RateListFragment  : Fragment() {

    private val viewModel: RateListViewModel by viewModel()
    private val ratesListAdapter = RateListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.refresh()

        rateList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ratesListAdapter
        }

        refreshLayout.setOnRefreshListener {
            rateList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeVielModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    fun observeVielModel(){

        viewModel.rates.observe(this, Observer {
            it?.let {
                rateList.visibility = View.VISIBLE
                ratesListAdapter.updateRateList(it)
            }
        })

        viewModel.ratesLoadError.observe(this, Observer {
            it?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                if(it){
                    loadingView.visibility = View.VISIBLE
                    listError.visibility = View.GONE
                    rateList.visibility = View.GONE
                }else {
                    loadingView.visibility = View.GONE
                }
            }
        })
    }

    fun removeObservers(){
        viewModel.loading.removeObservers(this)
        viewModel.ratesLoadError.removeObservers(this)
        viewModel.rates.removeObservers(this)
    }
}