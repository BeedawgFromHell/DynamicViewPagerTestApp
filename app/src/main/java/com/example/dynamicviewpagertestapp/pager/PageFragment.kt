package com.example.dynamicviewpagertestapp.pager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.dynamicviewpagertestapp.MainViewModel
import com.example.dynamicviewpagertestapp.databinding.PageFragmentBinding
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class PageFragment : Fragment() {

    companion object {
        const val NOTE_ID = "note_id"

        const val TEXT = "text"
        const val WEBPAGE = "webpage"

        const val UNDEFINED_TYPE_EX = "Undefined type"

        fun newInstance(noteId: Int): PageFragment {
            val fragment = PageFragment()
            fragment.arguments = bundleOf(NOTE_ID to noteId)
            return fragment
        }
    }

    private var binding: PageFragmentBinding? = null
    private val viewModel: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PageFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().getInt(NOTE_ID).let {
            viewModel.getPage(it)
        }

        lifecycleScope.launchWhenResumed {
            viewModel.pageStateFlow.collect {
                if (it != null) {
                    when (it.type) {
                        TEXT -> {
                            binding?.webContainer?.isGone = true
                            binding?.textContainer?.isVisible = true

                            binding?.textContainer?.text = it.payload.text
                        }
                        WEBPAGE -> {
                            binding?.webContainer?.apply {
                                webViewClient = WebViewClient()
                                settings.javaScriptEnabled = true
                                loadUrl(it.payload.url)
                            }
                        }
                        else -> {
                            throw RuntimeException(UNDEFINED_TYPE_EX)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}