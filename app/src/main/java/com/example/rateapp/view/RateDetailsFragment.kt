package com.example.rateapp.view

import androidx.fragment.app.Fragment
import com.example.rateapp.viewmodel.DetailsRateViewModel
import org.koin.android.viewmodel.ext.android.viewModel

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.palette.graphics.Palette

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.rateapp.R
import com.example.rateapp.databinding.FragmentRateDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel


class RateDetailsFragment : Fragment() {

    private val viewModel: DetailsRateViewModel by viewModel()
    protected lateinit var binding: FragmentRateDetailsBinding
    private var rateId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentRateDetailsBinding>(inflater, R.layout.fragment_rate_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            rateId = RateDetailsFragmentArgs.fromBundle(it).rateId
            viewModel.fetch(rateId)
        }

        observeViewlModel()
    }

    private fun observeViewlModel(){
        viewModel.rateLiveData.observe(this, Observer { rate ->
            rate?.let{
                binding.rateDetail = rate

            }
        })
    }

}