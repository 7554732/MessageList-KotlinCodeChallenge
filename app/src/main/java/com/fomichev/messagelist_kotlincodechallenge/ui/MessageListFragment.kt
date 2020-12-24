package com.fomichev.messagelist_kotlincodechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fomichev.messagelist_kotlincodechallenge.R
import com.fomichev.messagelist_kotlincodechallenge.databinding.FragmentMessageListBinding
import com.fomichev.messagelist_kotlincodechallenge.domain.MessageModel
import com.fomichev.messagelist_kotlincodechallenge.viewmodels.MessageListViewModel

class MessageListFragment : Fragment() {

    private val viewModel: MessageListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MessageListViewModel.Factory(activity.application))
            .get(MessageListViewModel::class.java)
    }

    private var messageListAdapter: MessageListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.messages.observe(viewLifecycleOwner, Observer<List<MessageModel>> { messages ->
            messages?.apply {
                messageListAdapter?.messages = messages
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding:FragmentMessageListBinding= DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_message_list,
            container,
            false)

        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        messageListAdapter = MessageListAdapter(
                MessageClick {
                    Toast.makeText(activity, "MessageClick " + it.id, Toast.LENGTH_LONG).show()
                },
                MessageLongClick {
                    Toast.makeText(activity, "MessageLongClick " + it.id, Toast.LENGTH_LONG).show()
                    viewModel.deleteMessagesFromRepository(listOf(it))
                })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageListAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}