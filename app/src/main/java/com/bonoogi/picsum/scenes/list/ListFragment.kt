package com.bonoogi.picsum.scenes.list

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonoogi.picsum.R
import com.bonoogi.picsum.databinding.FragmentListBinding
import com.bonoogi.picsum.scenes.ImageNavigator
import com.bonoogi.picsum.util.px
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@AndroidEntryPoint
class ListFragment : Fragment(), ImageListCallback {

    companion object {
        const val gridSpanCount = 3
    }

    private lateinit var navigator: ImageNavigator
    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<ListViewModel>()

    private val adapter: ImageListAdapter by lazy {
        ImageListAdapter(this)
    }

    private val itemDecoration: RecyclerView.ItemDecoration by lazy {

        val itemSpace = 8.px
        val spacePerItem: Int by lazy {
            (itemSpace * (gridSpanCount - 1)) / gridSpanCount
        }

        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.layoutManager is GridLayoutManager) {

                    when (parent.getChildAdapterPosition(view)) {
                        0 -> {
                            outRect.right = spacePerItem
                        }
                        (gridSpanCount - 1) -> {
                            outRect.left = spacePerItem
                        }
                        else -> {
                            outRect.left = spacePerItem / 2
                            outRect.right = spacePerItem / 2
                        }
                    }
                }
                outRect.bottom = itemSpace
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as? ImageNavigator ?: parentFragment as? ImageNavigator
                ?: throw IllegalArgumentException("Must Implement ImageNavigator")
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

    override fun onResume() {
        super.onResume()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.run {
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onSelectImage(id: String) {
        navigator.navigateToImage(id)
    }

    private fun setViews() {
        with(binding) {
            listView.adapter = adapter
            listView.layoutManager = GridLayoutManager(requireContext(), gridSpanCount)
            listView.addItemDecoration(itemDecoration)
        }
    }

    private fun bindViewModel() {
        binding.vm = viewModel
        viewModel.start()
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}