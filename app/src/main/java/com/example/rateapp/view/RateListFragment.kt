package com.example.rateapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import org.koin.android.viewmodel.ext.android.viewModel

import android.view.*
import androidx.navigation.Navigation
import com.example.rateapp.viewmodel.RateListViewModel


class RateListFragment  : Fragment() {

    private val viewModel: RateListViewModel by viewModel()

}