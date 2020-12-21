
package com.fomichev.messagelist_kotlincodechallenge.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel

@BindingAdapter("isNetworkError", "messages")
fun hideIfNetworkError(view: View, isNetworkError: Boolean, messages: List<MessageModel>?) {
    view.visibility = if (messages != null && messages.size > 0) View.GONE else View.VISIBLE

    if(isNetworkError) {
        view.visibility = View.GONE
    }
}
