package com.bonoogi.picsum.scenes.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bonoogi.picsum.data.PagingList
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.data.image.ImageRepository
import com.bonoogi.picsum.util.Refreshable
import com.bonoogi.picsum.util.ScrollLoadMore
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    val handle: SavedStateHandle,
    private val repository: ImageRepository
) : ViewModel(), Refreshable, ScrollLoadMore {

    private val disposeBag = CompositeDisposable()

    private val _listLiveData = MutableLiveData<List<Image>>()
    val listLiveData: LiveData<List<Image>> get() = _listLiveData

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing get() = _isRefreshing

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private var loadingMore: Boolean = false

    private var lastPage: Int = -1
    private var hasNext: Boolean = false

    fun start() {
        repository.imageListObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handlePagingList, this::handleError)
            .addTo(disposeBag)
    }

    override fun whenRefresh() {
        if (isRefreshing.value == true) return
        _isRefreshing.value = true
        repository.imageListObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { _isRefreshing.value = false }
            .doOnError { _isRefreshing.value = false }
            .subscribe(this::handlePagingList, this::handleError)
            .addTo(disposeBag)
    }

    override fun willScrollEnds() {
        if (!hasNext || loadingMore) return
        loadingMore = true
        repository.imageListObservable(lastPage + 1)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { loadingMore = false }
            .doOnError { loadingMore = false }
            .subscribe(this::handlePagingList, this::handleError)
            .addTo(disposeBag)
    }

    private fun handlePagingList(list: PagingList<Image>) {
        Log.d("TAG", "${list.currentPage}")
        if (list.currentPage == 1) {
            _listLiveData.value = list
        } else {
            val current = _listLiveData.value.orEmpty().toMutableList()
            current.addAll(list)
            _listLiveData.value = current
        }
        hasNext = list.hasNext
        lastPage = list.currentPage
    }

    private fun handleError(error: Throwable) {
        Log.e("TAG", "$error message ${error.message.orEmpty()}")
        error.localizedMessage?.let { _errorMessage.value = it }
    }
}