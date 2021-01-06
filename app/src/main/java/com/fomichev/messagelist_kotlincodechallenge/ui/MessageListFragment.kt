package com.fomichev.messagelist_kotlincodechallenge.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
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

        viewModel.messages.observe(viewLifecycleOwner, Observer<PagedList<MessageModel>>{
            messageListAdapter!!.submitList(it)
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
                    val message = it
                    if (messageListAdapter!!.multiSelect) {
                        if(!messageListAdapter!!.selectedMessages.remove(message)) {
                            messageListAdapter!!.selectedMessages.add(message)
                        }
                        viewModel.messages.value?.indexOf(message)?.let { it1 ->
                            messageListAdapter!!.notifyItemChanged(
                                it1
                            )
                        }
                    }
                },
                MessageLongClick {
                    val message = it
                    if (!messageListAdapter!!.multiSelect) {
                        startActionMode()
                        messageListAdapter!!.selectedMessages.add(message)
                        viewModel.messages.value?.indexOf(message)?.let { it1 ->
                            messageListAdapter!!.notifyItemChanged(
                                it1
                            )
                        }
                    }
                })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = messageListAdapter
        }


        // Observer for the netwok error.
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    fun startActionMode() {
        activity?.startActionMode(actionModeCallback)
    }

    val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu): Boolean {
            messageListAdapter!!.multiSelect = true
            menu.add("Delete")
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem?): Boolean {
            viewModel.deleteMessagesFromRepository(messageListAdapter!!.selectedMessages.toList())
            mode.finish()
            messageListAdapter!!.multiSelect = false
            messageListAdapter!!.selectedMessages.clear()
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            messageListAdapter!!.multiSelect = false
            messageListAdapter!!.selectedMessages.clear()
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}

