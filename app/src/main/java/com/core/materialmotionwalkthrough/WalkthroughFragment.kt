package com.core.materialmotionwalkthrough

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.core.materialmotionwalkthrough.databinding.FragmentWalkthroughBinding

private const val ARG_TITLE = "title"
private const val ARG_BODY = "body"
private const val ARG_DRAWABLE_RES = "drawableRes"


class WalkthroughFragment : Fragment() {
    private var title: String? = null
    private var body: String? = null
    private var drawableRes: Int? = null

    private var _binding: FragmentWalkthroughBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            body = it.getString(ARG_BODY)
            drawableRes = it.getInt(ARG_DRAWABLE_RES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentWalkthroughBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bodyTextView.text = body
        binding.titleTextView.text = title
        drawableRes?.let { binding.imageView.setImageResource(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, body: String, @DrawableRes imageRes: Int) =
            WalkthroughFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_BODY, body)
                    putInt(ARG_DRAWABLE_RES, imageRes)
                }
            }
    }
}