
package com.fomichev.messagelist_kotlincodechallenge.util

import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.google.android.material.card.MaterialCardView

@BindingAdapter("isNetworkError", "messages")
fun hideRefreshingIndicator(view: SwipeRefreshLayout, isNetworkError: Boolean, messages: PagedList<MessageModel>?) {
    if (messages != null && messages.loadedCount > 0) view.setRefreshing(false) else view.setRefreshing(true)

    if(isNetworkError) {
        view.setRefreshing(false)
    }
}

@BindingAdapter("isCardSelected")
fun setListItemSelected(view: MaterialCardView, isSelected: Boolean) {
    view.setChecked(isSelected)
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    if(URLUtil.isValidUrl(url) && url.contains(".jpg")) {
        Glide.with(imageView.context).load(url).into(imageView)
        imageView.visibility = View.VISIBLE
    }
    else{
        imageView.visibility = View.GONE
    }
}
