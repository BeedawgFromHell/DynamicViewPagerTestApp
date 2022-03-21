package com.example.dynamicviewpagertestapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dynamicviewpagertestapp.databinding.MainActivityBinding
import com.example.dynamicviewpagertestapp.pager.ViewPagerAdapter
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var binding: MainActivityBinding? = null
    private val viewModel: MainViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)

        viewModel.getAllNotes()
        lifecycleScope.launchWhenCreated {
            viewModel.notesStateFlow.collect {
                if (!it.isNullOrEmpty()) {

                    val list = mutableListOf(it.last()).apply {
                        addAll(it)
                        add(it.first())
                    }

                    binding?.pager?.apply {
                        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                            override fun onPageScrollStateChanged(state: Int) {
                                super.onPageScrollStateChanged(state)
                                if (state == ViewPager2.SCROLL_STATE_IDLE || state == ViewPager2.SCROLL_STATE_DRAGGING) {
                                    if (currentItem == 0)
                                        setCurrentItem(list.size - 2, false)
                                    else if (currentItem == list.size - 1)
                                        setCurrentItem(1, false)
                                }
                            }
                        })
                        adapter = ViewPagerAdapter(this@MainActivity, list)
                        setCurrentItem(1, false)
                    }

                }
            }
        }

    }
}