package com.example.tutorsworldmobileapp.models

open class TutorDTO(
    // General Info
    var fullName: String? = null,
    var cnic: String? = null,
    var gender: String? = null,
    var age: Int = 0,
    var dob: String? = null,
    var religion: String? = null,
    var nationality: String? = null,
    var maritalStatus: String? = null,
    var city: String? = null,
    var province: String? = null,
    var country: String? = null,
    var subjects: String? = null,
    var contactEmail: String? = null,
    var contactNo: String? = null,
    var pAddress: String? = null,
    var tAddress: String? = null,
    var username: String? = null,
    var password: String? = null,
    var teachingSource: String? = null,
    var feeType: String? = null,
    var totalExperienceYears: Int = 0,

    // File Handling in Android (typically paths or URIs)
    var profileImgPath: String? = null,
    var resumePdfPath: String? = null,

    // Lists for binding
    var classes: MutableList<String> = mutableListOf(),
    var qualifications: MutableList<Qualification> = mutableListOf(),
    var experiences: MutableList<Experience> = mutableListOf()
)


open class TutorClasses(
    val className: String? = null,
    val tutorID: Long = 0
)

class TutorProfile : TutorDTO() {
    var tutorID: Long = 0
    // Note: tutorClasses uses the specific class type from your C# code
    var tutorClasses: MutableList<TutorClasses> = mutableListOf()
}

open class PaginatedTutorResponse(
    val tutors: List<TutorProfile> = emptyList(),
    val totalCount: Int = 0
)