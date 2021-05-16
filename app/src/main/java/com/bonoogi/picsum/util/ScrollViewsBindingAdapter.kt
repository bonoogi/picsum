package com.bonoogi.picsum.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface Refreshable {
    fun whenRefresh()
}

interface ScrollLoadMore {
    fun willScrollEnds()
}

object ScrollViewsBindingAdapter {
    @JvmStatic
    @BindingAdapter("is_refreshing")
    fun bindIsRefreshing(refreshLayout: SwipeRefreshLayout, isRefreshing: Boolean?) {
        refreshLayout.isRefreshing = isRefreshing ?: false
    }

    @JvmStatic
    @BindingAdapter("refreshable")
    fun bindWhenRefresh(refreshLayout: SwipeRefreshLayout, refreshable: Refreshable) {
        refreshLayout.setOnRefreshListener { refreshable.whenRefresh() }
    }

    @JvmStatic
    @BindingAdapter("scroll_load_more")
    fun bindWillScrollEnds(recyclerView: RecyclerView, scrollLoadMore: ScrollLoadMore) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as? LinearLayoutManager)?.let { lm ->
                    val offset = if (lm is GridLayoutManager) lm.spanCount * 2 else 2
                    val lastVisiblePos = lm.findLastVisibleItemPosition()
                    lm.itemCount.takeIf { it > offset }?.let { it - offset }?.let { willEndPos ->
                        if (lastVisiblePos > willEndPos) scrollLoadMore.willScrollEnds()
                    }
                } ?: run {
                    val viewHeight = recyclerView.height.takeIf { it > 0 } ?: return
                    val scrollHeight = recyclerView.getChildAt(0)?.height?.takeIf { it > 0 } ?: return
                    if (recyclerView.scrollY > (scrollHeight - viewHeight - 100.px)) {
                        scrollLoadMore.willScrollEnds()
                    }
                }
            }
        })
    }
}