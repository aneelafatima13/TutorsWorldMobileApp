
package com.example.tutorsworldmobileapp.adapters
import android.view.LayoutInflater
import com.example.tutorsworldmobileapp.R

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.models.TutorProfile

class TutorAdapter(private val tutorList: List<TutorProfile>) :
    RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

    class TutorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtName)
        val subject: TextView = view.findViewById(R.id.txtSubject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tutor, parent, false)
        return TutorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
        val tutor = tutorList[position]
        holder.name.text = tutor.fullName
        holder.subject.text = tutor.subjects
    }

    override fun getItemCount() = tutorList.size
}