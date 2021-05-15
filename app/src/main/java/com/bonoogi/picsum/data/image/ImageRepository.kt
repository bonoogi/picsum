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
    fun imageObservable(id: String): Maybe<Image>
}

class ImageRepositoryImpl @Inject constructor(
    private val source: ImageRemoteSource
): ImageRepository {

    override fun imageListObservable(page: Int): Observable<PagingList<Image>> {
        return source.getImagesResult(page)
            .subscribeOn(Schedulers.io())
    }

    override fun imageObservable(id: String): Maybe<Image> {
        return Maybe.empty()
    }
}