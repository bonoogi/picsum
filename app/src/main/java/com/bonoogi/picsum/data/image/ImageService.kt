package com.bonoogi.picsum.data.image

import io.reactivex.rxjava3.core.Single
import retrofit2.adapter.rxjava3.Result
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface ImageService {

    @GET("v2/list")
    fun getListSingle(@Query("page") page: Int): Single<Result<List<Image>>>
}