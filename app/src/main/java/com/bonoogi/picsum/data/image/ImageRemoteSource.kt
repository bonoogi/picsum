package com.bonoogi.picsum.data.image

import com.bonoogi.picsum.data.PagingList
import io.reactivex.rxjava3.core.Observable
import okhttp3.Headers
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
            val list: PagingList<Image> = result.response()?.let { response ->
                val list = response.body().orEmpty()
                val prevOrNext = isLinkHasPrevOrNext(response.headers())
                PagingList(list, page, hasPrev = prevOrNext.first, hasNext = prevOrNext.second)
            } ?: return@flatMapObservable Observable.empty()
            Observable.just(list)
        }
    }

    /**
     * API 응답 헤더의 `Link`에 해당하는 문자중에 이전, 다음 페이지가 있는지 확인해
     * 첫번째 값은 이전 페이지가 있는지 여부, 두번째 값은 다음 페이지가 있는지 여부를 담은 `Pair`를 반환한다.
     * - Parameters:
     *     - header: `Link` 값을 추출할 API 응답 헤더
     * - Return: `first`는 이전 페이지가 있는지, `second`는 다음 페이지가 있는지를 알려주는 `Pair`
     */
    private fun isLinkHasPrevOrNext(header: Headers): Pair<Boolean, Boolean> {
        val links = header.get("Link")?.split(",").orEmpty()
        if (links.size < 2) return Pair(first = false, second = false)

        var hasPrev = false
        var hasNext = false
        for (link in links) {
            val segments = link.split(";")
            if (segments.size < 2) break
            val rel = segments.firstOrNull { it.startsWith("rel") } ?: break
            val prevOrNext = rel.split("=").getOrNull(1)?.trim() ?: break
            when (prevOrNext) {
                "\"prev\"" -> { hasPrev = true }
                "\"next\"" -> { hasNext = false }
            }
        }
        return Pair(hasPrev, hasNext)
    }
}