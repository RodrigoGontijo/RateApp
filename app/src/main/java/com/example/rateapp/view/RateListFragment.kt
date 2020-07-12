package com.example.rateapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rateapp.R
import com.example.rateapp.RateConstants.Companion.NUM_COLLUMS
import com.example.rateapp.viewmodel.RateListViewModel
import kotlinx.android.synthetic.main.fragment_rate_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class RateListFragment  : Fragment() {

    private val viewModel: RateListViewModel by viewModel()
    private val mImageUrls: ArrayList<String> = ArrayList()
    private val ratesListAdapter = RateListAdapter(arrayListOf())
    private val staggeredGridLayoutManager = StaggeredGridLayoutManager(NUM_COLLUMS, LinearLayout.VERTICAL)

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
            layoutManager = staggeredGridLayoutManager
            adapter = ratesListAdapter
        }

        refreshLayout.setOnRefreshListener {
            rateList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }
        initImageBitmaps()
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
                it.rateListModel.forEach{
                    it.imagesRandom = mImageUrls.random()
                }
                ratesListAdapter.updateRateList(it.rateListModel)
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

    private fun initImageBitmaps() {
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg")

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg")

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg")

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg")

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg")

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg")

        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg")

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg")

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg")

    }

}