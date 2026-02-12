package com.example.tutorsworldmobileapp.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TutorApi {

    @Multipart
    @POST("api/Tutor/SaveTutor")
    suspend fun registerTutor(
        @Part parts: List<MultipartBody.Part>
    ): Response<ResponseBody>
}
