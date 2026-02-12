package com.example.tutorsworldmobileapp
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
class RegisterTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_type)

        findViewById<View>(R.id.cardStudent).setOnClickListener {
            startActivity(Intent(this, StudentRegisterActivity::class.java))
        }

        findViewById<View>(R.id.cardParent).setOnClickListener {
            startActivity(Intent(this, ParentRegisterActivity::class.java))
        }

        findViewById<View >(R.id.cardTutor).setOnClickListener {
            startActivity(Intent(this, TutorRegisterActivity::class.java))
        }
    }
}
