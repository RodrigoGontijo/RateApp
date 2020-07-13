package com.example.rateapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.rateapp.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(
    uri: String?,
    progressDrawable: CircularProgressDrawable,
    centerCropEnabled: Boolean
) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)
        .priority(Priority.HIGH)
        .skipMemoryCache(true)

    if (centerCropEnabled)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .placeholder(progressDrawable)
            .centerCrop()
            .into(this)
    else
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .placeholder(progressDrawable)
            .fitCenter()
            .into(this)
}

@BindingAdapter(value = ["android:imageUrl", "android:centerCropEnabled"], requireAll = true)
fun loadImage(view: ImageView, url: String?, centerCropEnabled: Boolean) {
    view.loadImage(url, getProgressDrawable(view.context), centerCropEnabled)
}