package za.co.gundula.app.network

import io.reactivex.Single
import retrofit2.http.GET
import za.co.gundula.app.model.CatRemoteModel

interface CatApi {

    //api/images/get?format=json&results_per_page=100&size=small&type=png
    @GET("api/images/get?format=json&results_per_page=100&size=small&type=png")
    fun getCats(): Single<List<CatRemoteModel>>
}