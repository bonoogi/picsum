package com.bonoogi.picsum.data.image

import com.bonoogi.picsum.data.PagingList
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface ImageRepository {
    fun imageListObservable(page: Int = 1): Observable<PagingList<Image>>
    fun getImageMaybe(id: String): Maybe<Image>
}

class ImageRepositoryImpl @Inject constructor(
    private val remote: ImageRemoteSource,
    private val local: ImageDao
): ImageRepository {

    override fun imageListObservable(page: Int): Observable<PagingList<Image>> {
        return remote.getImagesResult(page)
            .subscribeOn(Schedulers.io())
            .flatMap { result ->
                local.insertImages(result).andThen(Observable.just(result))
            }
    }

    override fun getImageMaybe(id: String): Maybe<Image> {
        return local.get(id).subscribeOn(Schedulers.io())
    }
}