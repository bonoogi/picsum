package com.bonoogi.picsum.data.image

import com.bonoogi.picsum.data.PagingList
import com.bonoogi.picsum.util.splitAndTrim
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface ImageRemoteSource {
    fun getImagesResult(page: Int = 1): Observable<PagingList<Image>>
}

class ImageRemoteSourceImpl @Inject constructor(
    private val service: ImageService
): ImageRemoteSource {
    override fun getImagesResult(page: Int): Observable<PagingList<Image>> {
        return service.getListSingle(page).flatMapObservable { result ->
            result.error()?.let { return@flatMapObservable Observable.error(it) }
            result.response()?.let { response ->
                var hasPrev = false
                var hasNext = false
                val links = response.headers().get("Link")?.splitAndTrim(",").orEmpty()
                for (link in links) {
                    val segments = link.splitAndTrim(";")
                    if (segments.size < 2) break
                    hasPrev = segments.contains("rel=\"prev\"")
                    hasNext = segments.contains("rel=\"next\"")
                }
                val list = response.body().orEmpty()
                Observable.just(PagingList(list, hasPrev, hasNext, page))
            } ?: Observable.empty()
        }
    }
}