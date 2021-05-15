package com.bonoogi.picsum.data.network

import com.bonoogi.picsum.data.response.Photo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface PicsumService {

    @GET("v2/list")
    fun list(@Query("page") page: Int): Single<List<Photo>>
}