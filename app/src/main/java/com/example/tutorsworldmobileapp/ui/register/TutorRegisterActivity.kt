package com.example.tutorsworldmobileapp

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.models.Experience
import com.example.tutorsworldmobileapp.models.Qualification
import com.example.tutorsworldmobileapp.models.TutorDTO
import com.example.tutorsworldmobileapp.network.RetrofitClient
import com.example.tutorsworldmobileapp.utils.buildTutorMultipart
import kotlinx.coroutines.launch

class TutorRegisterActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_PICK_CODE = 101
        private const val PDF_PICK_CODE = 102
    }

    // ---------- UI ELEMENTS ----------
    private lateinit var imgProfile: ImageView
    private lateinit var tvResumeName: TextView

    private lateinit var btnPickImage: Button
    private lateinit var btnPickResume: Button
    private lateinit var btnSubmit: Button

    private lateinit var rvQualifications: RecyclerView
    private lateinit var btnAddQualification: Button
    private lateinit var rvExperience: RecyclerView
    private lateinit var btnAddExperience: Button

    private var imageUri: Uri? = null
    private var pdfUri: Uri? = null

    // ---------- DATA ----------
    private val qualifications = mutableListOf<Qualification>()
    private lateinit var qualificationAdapter: QualificationAdapter

    private val experiences = mutableListOf<Experience>()
    private lateinit var experienceAdapter: ExperienceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutor_register)

        // ---------- FIND VIEWS ----------
        imgProfile = findViewById(R.id.imgProfile)
        tvResumeName = findViewById(R.id.tvResumeName)
        btnPickImage = findViewById(R.id.btnPickImage)
        btnPickResume = findViewById(R.id.btnPickResume)
        btnSubmit = findViewById(R.id.btnSubmit)

        rvQualifications = findViewById(R.id.rvQualifications)
        btnAddQualification = findViewById(R.id.btnAddQualification)
        rvExperience = findViewById(R.id.rvExperience)
        btnAddExperience = findViewById(R.id.btnAddExperience)

        // ---------- IMAGE / PDF PICKERS ----------
        btnPickImage.setOnClickListener { pickImage() }
        btnPickResume.setOnClickListener { pickPdf() }


        if (qualifications.isEmpty()) {
            qualifications.add(Qualification())
        }
        if (experiences.isEmpty()) {
            experiences.add(Experience())
        }
        // ---------- SETUP QUALIFICATIONS RECYCLER ----------
        qualificationAdapter = QualificationAdapter(qualifications)
        rvQualifications.layoutManager = LinearLayoutManager(this)
        rvQualifications.adapter = qualificationAdapter

        btnAddQualification.setOnClickListener {
            qualifications.add(Qualification())
            qualificationAdapter.notifyItemInserted(qualifications.size - 1)
        }



        // ---------- SETUP EXPERIENCE RECYCLER ----------
        experienceAdapter = ExperienceAdapter(experiences)
        rvExperience.layoutManager = LinearLayoutManager(this)
        rvExperience.adapter = experienceAdapter

        btnAddExperience.setOnClickListener {
            experiences.add(Experience())
            experienceAdapter.notifyItemInserted(experiences.size - 1)
        }

        // ---------- SUBMIT ----------
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                submitForm()
            }
        }
    }

    // ---------- PICK IMAGE ----------
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    // ---------- PICK PDF ----------
    private fun pickPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "application/pdf" }
        startActivityForResult(intent, PDF_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    imageUri = data.data
                    imgProfile.setImageURI(imageUri)
                }
                PDF_PICK_CODE -> {
                    pdfUri = data.data
                    tvResumeName.text = getFileName(pdfUri)
                }
            }
        }
    }

    private fun getFileName(uri: Uri?): String {
        var result = "Resume Selected"
        uri ?: return result
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) result = it.getString(index)
            }
        }
        return result
    }

    // ---------- FORM VALIDATION ----------
    private fun validateForm(): Boolean {
        val fullName = findViewById<EditText>(R.id.etFullName)
        val email = findViewById<EditText>(R.id.etEmail)
        val phone = findViewById<EditText>(R.id.etPhone)
        val gender = findViewById<RadioGroup>(R.id.rgGender)

        if (fullName.text.isNullOrEmpty()) {
            fullName.error = "Full Name is required"
            return false
        }

        if (email.text.isNullOrEmpty()) {
            email.error = "Email is required"
            return false
        }

        if (phone.text.isNullOrEmpty()) {
            phone.error = "Phone is required"
            return false
        }

        if (gender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select profile image", Toast.LENGTH_SHORT).show()
            return false
        }

        if (pdfUri == null) {
            Toast.makeText(this, "Please select resume PDF", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // ---------- FORM SUBMIT ----------
    private fun submitForm() {
        val tutor = TutorDTO().apply {
            fullName = findViewById<EditText>(R.id.etFullName).text.toString()
            cnic = findViewById<EditText>(R.id.etCNIC).text.toString()
            age = findViewById<EditText>(R.id.etAge).text.toString().toIntOrNull() ?: 0
            city = findViewById<EditText>(R.id.etCity).text.toString()
            contactEmail = findViewById<EditText>(R.id.etEmail).text.toString()
            contactNo = findViewById<EditText>(R.id.etPhone).text.toString()
            username = findViewById<EditText>(R.id.etUsername).text.toString()
            password = findViewById<EditText>(R.id.etPassword).text.toString()
        }

        val genderId = findViewById<RadioGroup>(R.id.rgGender).checkedRadioButtonId
        tutor.gender = findViewById<RadioButton>(genderId).text.toString()

        // Attach dynamic data
        tutor.qualifications = qualifications
        tutor.experiences = experiences

        lifecycleScope.launch {
            try {
                // Pass "this" as the context, then the model and the two URIs
                val parts = buildTutorMultipart(this@TutorRegisterActivity, tutor, imageUri!!, pdfUri!!)
                val response = RetrofitClient.api.registerTutor(parts)

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@TutorRegisterActivity,
                        "Tutor Registered Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@TutorRegisterActivity,
                        "Registration Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@TutorRegisterActivity,
                    e.message ?: "Something went wrong",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ---------- ACCESSORS ----------
    fun getProfileImageUri(): Uri? = imageUri
    fun getResumePdfUri(): Uri? = pdfUri
}
