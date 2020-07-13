package com.example.rateapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.rateapp.R
import com.example.rateapp.databinding.ItemRateBinding
import com.example.rateapp.model.raterepository.RateModel
import kotlinx.android.synthetic.main.fragment_rate_details.view.*
import kotlinx.android.synthetic.main.item_rate.view.*


class RateListAdapter(val rateList: ArrayList<RateModel>) :
    RecyclerView.Adapter<RateListAdapter.RateListViewHolder>(), RateItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemRateBinding>(inflater, R.layout.item_rate, parent, false)
        return RateListViewHolder(view)
    }

    override fun getItemCount() = rateList.size


    override fun onBindViewHolder(holder: RateListViewHolder, position: Int) {
        holder.view.rate = rateList[position]
        holder.view.listener = this
    }

    override fun onItemRateClick(v: View, imageUrl: String, rateId: Int) {
        val action = RateListFragmentDirections.actionDetailFragment()
        action.rateId = rateId
        action.imageUrl = imageUrl
        Navigation.findNavController(v).navigate(action)
    }

    fun updateRateList(newRateList: List<RateModel>) {
        rateList.clear()
        rateList.addAll(newRateList)
        notifyDataSetChanged()
    }


    class RateListViewHolder(var view: ItemRateBinding) : RecyclerView.ViewHolder(view.root)

}