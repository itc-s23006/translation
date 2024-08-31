package jp.ac.it_college.std.s23006.translation

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationService {
    @POST("/translate")
    fun translate(
        @Query("q") text: String,
        @Query("source") sourceLang: String = "en",
        @Query("target") targetLang: String = "ja"
    ): Call<TranslationResponse>
}
