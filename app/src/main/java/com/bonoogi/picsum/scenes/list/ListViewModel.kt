package com.bonoogi.picsum.scenes.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bonoogi.picsum.data.PagingList
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.data.image.ImageRepository
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
) : ViewModel() {

    private val disposeBag = CompositeDisposable()

    private val _listLiveData = MutableLiveData<PagingList<Image>>()
    val listLiveData: LiveData<PagingList<Image>> get() = _listLiveData

    fun start() {
        repository.imageListObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _listLiveData.value = it
                }, {
                    Log.e("TAG", it.toString())
                }
            )
            .addTo(disposeBag)
    }

    fun refresh() {
        repository.imageListObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _listLiveData.value = it
                }, {
                    Log.e("TAG", it.toString())
                }
            )
            .addTo(disposeBag)
    }

    fun loadNext() {

    }
}