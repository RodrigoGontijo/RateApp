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


class RateListFragment : Fragment() {

    private val viewModel: RateListViewModel by viewModel()
    private val mImageUrls: ArrayList<String> = ArrayList()
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
        viewModel.refresh()

        rateList.apply {
            layoutManager = StaggeredGridLayoutManager(NUM_COLLUMS, LinearLayout.VERTICAL)
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

    fun observeVielModel() {

        viewModel.rates.observe(this, Observer {
            it?.let {
                rateList.visibility = View.VISIBLE
                it.rateListModel.forEach {
                    it.imagesRandom = mImageUrls.random()
                }
                ratesListAdapter.updateRateList(it.rateListModel)
            }
        })

        viewModel.ratesLoadError.observe(this, Observer {
            it?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                if (it) {
                    loadingView.visibility = View.VISIBLE
                    listError.visibility = View.GONE
                    rateList.visibility = View.GONE
                } else {
                    loadingView.visibility = View.GONE
                }
            }
        })
    }

    fun removeObservers() {
        viewModel.loading.removeObservers(this)
        viewModel.ratesLoadError.removeObservers(this)
        viewModel.rates.removeObservers(this)
    }

    private fun initImageBitmaps() {
        mImageUrls.add("https://images.pexels.com/photos/4401993/pexels-photo-4401993.jpeg?cs=srgb&dl=black-concrete-building-4401993.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/380768/pexels-photo-380768.jpeg?cs=srgb&dl=architectural-design-architecture-ceiling-chairs-380768.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/380769/pexels-photo-380769.jpeg?cs=srgb&dl=adult-business-computer-contemporary-380769.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/683039/pexels-photo-683039.jpeg?cs=srgb&dl=assorted-decors-with-brown-rack-inside-store-683039.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1855214/pexels-photo-1855214.jpeg?cs=srgb&dl=food-inside-display-chiller-1855214.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/2159065/pexels-photo-2159065.jpeg?cs=srgb&dl=people-in-cafeteria-2159065.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/933964/pexels-photo-933964.jpeg?cs=srgb&dl=group-of-friends-hanging-out-933964.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1283219/pexels-photo-1283219.jpeg?cs=srgb&dl=assorted-wine-bottles-1283219.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1005639/pexels-photo-1005639.jpeg?cs=srgb&dl=two-person-in-bar-stools-1005639.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1426620/pexels-photo-1426620.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500")

        mImageUrls.add("https://images.pexels.com/photos/2835547/pexels-photo-2835547.jpeg?cs=srgb&dl=architectural-design-of-a-bar-2835547.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1801106/pexels-photo-1801106.jpeg?cs=srgb&dl=colorful-indoor-lights-1801106.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1378424/pexels-photo-1378424.jpeg?cs=srgb&dl=empty-room-1378424.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/247931/pexels-photo-247931.jpeg?cs=srgb&dl=chairs-and-tables-in-restaurant-247931.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/2253643/pexels-photo-2253643.jpeg?cs=srgb&dl=top-view-photo-of-restaurant-2253643.jpg&fm=jpg")

        mImageUrls.add("https://images.pexels.com/photos/1128259/pexels-photo-1128259.jpeg?cs=srgb&dl=selective-focus-photography-of-assorted-brand-liquor-bottles-1128259.jpg&fm=jpg")


    }

}