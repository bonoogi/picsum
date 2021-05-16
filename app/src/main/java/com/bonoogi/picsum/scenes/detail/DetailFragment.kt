package com.bonoogi.picsum.scenes.detail

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bonoogi.picsum.R
import com.bonoogi.picsum.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    companion object {
        private const val KEY_ID = "IMAGE_ID"

        fun newInstance(imageId: String) = DetailFragment().apply {
            arguments = Bundle().also {
                it.putString(KEY_ID, imageId)
            }
        }
    }

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var imageId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)

        arguments?.getString(KEY_ID)?.let { imageId = it } ?: run {
            Toast.makeText(requireContext(), R.string.detail_error_invalid_id, Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStackImmediate()
            return
        }
        bindViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                parentFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindViewModel() {
        binding.vm = viewModel
        viewModel.startWithId(imageId)
    }
}