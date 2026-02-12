package com.example.tutorsworldmobileapp.utils

import com.example.tutorsworldmobileapp.models.TutorDTO
import okhttp3.MultipartBody

fun buildTutorMultipart(model: TutorDTO): List<MultipartBody.Part> {

    val parts = mutableListOf<MultipartBody.Part>()

    fun add(key: String, value: String?) {
        parts.add(MultipartBody.Part.createFormData(key, value ?: ""))
    }

    add("FullName", model.fullName)
    add("CNIC", model.cnic)
    add("Gender", model.gender)
    add("Age", model.age.toString())
    add("City", model.city)
    add("ContactEmail", model.contactEmail)
    add("ContactNo", model.contactNo)
    add("Username", model.username)
    add("Password", model.password)

    model.classes.forEachIndexed { i, c ->
        add("Classes[$i]", c)
    }

    model.qualifications.forEachIndexed { i, q ->
        add("Qualifications[$i].Institute", q.institute)
        add("Qualifications[$i].Degree", q.degree)
        add("Qualifications[$i].PassingYear", q.passingYear.toString())
        add("Qualifications[$i].Percentage", q.percentage)
    }

    model.experiences.forEachIndexed { i, e ->
        add("Experiences[$i].ExpInstitute", e.expInstitute)
        add("Experiences[$i].ExpStart", e.expStart)
        add("Experiences[$i].ExpEnd", e.expEnd)
        add("Experiences[$i].ExpDuration", e.expDuration)
    }

    return parts
}
