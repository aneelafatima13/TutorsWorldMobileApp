package com.example.tutorsworldmobileapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.adapters.TutorAdapter
import com.example.tutorsworldmobileapp.models.TutorProfile
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navigationView)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                }
                R.id.nav_logout -> {
                    finish()
                }

                R.id.register -> {
                    startActivity(Intent(this, RegisterTypeActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            true
        }

        setupRecycler()
    }

    private fun setupRecycler() {
        val rv = findViewById<RecyclerView>(R.id.rvTutors)
        rv.layoutManager = LinearLayoutManager(this)

        val sampleTutors = listOf(
            TutorProfile().apply {
                fullName = "Ali Khan"
                subjects = "Math, Physics"
            },
            TutorProfile().apply {
                fullName = "Ayesha Malik"
                subjects = "English, IELTS"
            },
            TutorProfile().apply {
                fullName = "Usman Ahmed"
                subjects = "Chemistry"
            }
        )

        rv.adapter = TutorAdapter(sampleTutors)
    }
}
