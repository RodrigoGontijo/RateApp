package com.example.rateapp.view

import androidx.fragment.app.Fragment
import com.example.rateapp.viewmodel.RateDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.example.rateapp.R
import com.example.rateapp.databinding.FragmentRateDetailsBinding
import kotlinx.android.synthetic.main.fragment_rate_details.*
import kotlinx.android.synthetic.main.fragment_rate_list.*
import kotlinx.android.synthetic.main.fragment_rate_list.listError
import kotlinx.android.synthetic.main.fragment_rate_list.loadingView
import kotlinx.android.synthetic.main.fragment_rate_list.refreshLayout


class RateDetailsFragment : Fragment() {

    private val detailsViewModel: RateDetailsViewModel by viewModel()
    protected lateinit var binding: FragmentRateDetailsBinding
    private var rateId = 0
    private var imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentRateDetailsBinding>(
            inflater,
            R.layout.fragment_rate_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            rateId = RateDetailsFragmentArgs.fromBundle(it).rateId
            imageUrl = RateDetailsFragmentArgs.fromBundle(it).imageUrl
            detailsViewModel.fetchFromRemote(rateId)
            binding.imageUrl = imageUrl
        }

        observeViewlModel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        removeObservers()
    }

    private fun observeViewlModel() {
        detailsViewModel.rate.observe(this, Observer {
            it?.let {
                rateContainer.visibility = View.VISIBLE
                binding.rateDetail = it

                it.schedule?.friday?.open?.apply {
                    binding.rateSchedule.text =
                        getString(R.string.util_day).plus(": ").plus(it.schedule.friday.open)
                            .plus(" - ").plus(it.schedule.friday.close)
                }

                it.schedule?.saturday?.open?.apply {

                    if(it.schedule.friday.open != null){
                        binding.rateSchedule.text = binding.rateSchedule.text.toString().plus("\n")
                    }

                    binding.rateSchedule.text = binding.rateSchedule.text.toString().plus(
                        getString(R.string.weekend_days).plus(": ")
                            .plus(it.schedule.saturday.open)
                            .plus(" - ").plus(it.schedule.saturday.close).plus("")
                    )
                }

            }
        })

        detailsViewModel.rateLoadError.observe(this, Observer {
            it?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        detailsViewModel.loading.observe(this, Observer {
            it?.let {
                if (it) {
                    loadingView.visibility = View.VISIBLE
                    listError.visibility = View.GONE
                    rateContainer.visibility = View.GONE
                } else {
                    loadingView.visibility = View.GONE
                }
            }
        })
    }


    fun removeObservers() {
        detailsViewModel.loading.removeObservers(this)
        detailsViewModel.rateLoadError.removeObservers(this)
        detailsViewModel.rate.removeObservers(this)
    }

}