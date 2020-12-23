
package com.fomichev.messagelist_kotlincodechallenge.util

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel

@BindingAdapter("isNetworkError", "messages")
fun hideRefreshingIndicator(view: SwipeRefreshLayout, isNetworkError: Boolean, messages: List<MessageModel>?) {
    if (messages != null && messages.size > 0) view.setRefreshing(false) else view.setRefreshing(true)

    if(isNetworkError) {
        view.setRefreshing(false)
    }
}
