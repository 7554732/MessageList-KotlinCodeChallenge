
package com.fomichev.messagelist_kotlincodechallenge.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

/**
*   for     app:onLongClick="@{() -> messageLongClick.onLongClick(message)}"
*/
@BindingAdapter("onLongClick")
fun setOnLongClickListener(view: View, listener: Runnable) {
    view.setOnLongClickListener { listener.run(); true }
}