package com.bonoogi.picsum.data.image

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface ImageRepository {
    fun imageListObservable(page: Int = 1): Observable<List<Image>>
    fun imageObservable(id: String): Maybe<Image>
}

class ImageRepositoryImpl(
    private val service: ImageService
): ImageRepository {

    override fun imageListObservable(page: Int): Observable<List<Image>> {
        return Observable.empty()
    }

    override fun imageObservable(id: String): Maybe<Image> {
        return Maybe.empty()
    }
}