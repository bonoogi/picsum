package com.bonoogi.picsum.scenes.list

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonoogi.picsum.R
import com.bonoogi.picsum.databinding.FragmentListBinding
import com.bonoogi.picsum.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<ListViewModel>()

    private val adapter: ImageListAdapter by lazy {
        ImageListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        setViews()
        bindViewModel()
    }

    private fun setViews() {
        with(binding) {
            listView.adapter = adapter
            listView.layoutManager = GridLayoutManager(requireContext(), 3)

            val verticalPadding = 8.px
            val horizontalPaddingTotal = 42.px
            listView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val pos = parent.getChildAdapterPosition(view)
                    if (pos < 3) outRect.top = verticalPadding
                    outRect.bottom = verticalPadding
                    val horizontalPaddingPerItem = horizontalPaddingTotal / 3
                    when (pos % 3) {
                        // 좌
                        0 -> {
                            outRect.left = 12.px
                            outRect.right = abs(12.px - horizontalPaddingPerItem)
                        }
                        // 중
                        1 -> {
                            outRect.left = horizontalPaddingPerItem / 2
                            outRect.right = horizontalPaddingPerItem / 2
                        }
                        // 우
                        else -> {
                            outRect.left = abs(12.px - horizontalPaddingPerItem)
                            outRect.right = 12.px
                        }
                    }
                }
            })
        }
    }

    private fun bindViewModel() {
        viewModel.start()
        viewModel.listLiveData
            .observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
    }
}