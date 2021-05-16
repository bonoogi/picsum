package com.bonoogi.picsum.scenes.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.data.image.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> get() = _image

    fun startWithId(imageId: String) {
        repository.getImageMaybe(imageId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleImage, this::handleError) {
                // 이미지 찾을 수 없음
                Log.d("TAG", "이미지 없음")
            }
    }

    private fun handleImage(image: Image) {
        Log.d("TAG", image.toString())
        _image.value = image
    }

    private fun handleError(error: Throwable) {
        Log.e("TAG", "$error message ${error.localizedMessage}")
    }
}