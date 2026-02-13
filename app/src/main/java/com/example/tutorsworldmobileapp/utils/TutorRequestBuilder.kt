// utils/MultipartHelper.kt
package com.example.tutorsworldmobileapp.utils

import android.content.Context
import android.net.Uri
import com.example.tutorsworldmobileapp.models.TutorDTO
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun buildTutorMultipart(
    context: Context,
    model: TutorDTO,
    imageUri: Uri,
    pdfUri: Uri
): List<MultipartBody.Part> {

    val parts = mutableListOf<MultipartBody.Part>()

    // Helper for strings
    fun add(key: String, value: String?) {
        parts.add(MultipartBody.Part.createFormData(key, value ?: ""))
    }

    // 1. Add Text Fields
    add("FullName", model.fullName)
    add("CNIC", model.cnic)
    add("Gender", model.gender)
    add("Age", model.age.toString())
    add("City", model.city)
    add("ContactEmail", model.contactEmail)
    add("ContactNo", model.contactNo)
    add("Username", model.username)
    add("Password", model.password)

    // 2. Add Collections
    model.qualifications.forEachIndexed { i, q ->
        add("Qualifications[$i].Institute", q.institute)
        add("Qualifications[$i].Degree", q.degree)
        add("Qualifications[$i].PassingYear", q.passingYear.toString())
        add("Qualifications[$i].Percentage", q.percentage)
    }

    model.experiences.forEachIndexed { i, e ->
        add("Experiences[$i].ExpInstitute", e.institute)
        add("Experiences[$i].ExpStart", e.startDate)
        add("Experiences[$i].ExpEnd", e.endDate)
        add("Experiences[$i].ExpDuration", e.duration)
    }

    // 3. Add Files (The missing part)
    context.contentResolver.openInputStream(imageUri)?.readBytes()?.let {
        val requestBody = it.toRequestBody("image/*".toMediaTypeOrNull())
        parts.add(MultipartBody.Part.createFormData("ProfileImage", "profile.jpg", requestBody))
    }

    context.contentResolver.openInputStream(pdfUri)?.readBytes()?.let {
        val requestBody = it.toRequestBody("application/pdf".toMediaTypeOrNull())
        parts.add(MultipartBody.Part.createFormData("ResumeFile", "resume.pdf", requestBody))
    }

    return parts
}