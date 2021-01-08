package com.fomichev.messagelist_kotlincodechallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fomichev.messagelist_kotlincodechallenge.R
import com.fomichev.messagelist_kotlincodechallenge.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentImageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_image,
            container,
            false)

        val args = ImageFragmentArgs.fromBundle(requireArguments())

        this.context?.let {
            Glide.with(it)
                .load(args.imageUrl)
                .into(binding.imageView)
        }

        return binding.root
    }
}

