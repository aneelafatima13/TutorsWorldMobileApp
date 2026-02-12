package com.example.tutorsworldmobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.models.Experience

class ExperienceAdapter(
    private val experiences: MutableList<Experience>
) : RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {

    inner class ExperienceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etInstitute: EditText = itemView.findViewById(R.id.etExpInstitute)
        val etStart: EditText = itemView.findViewById(R.id.etExpStart)
        val etEnd: EditText = itemView.findViewById(R.id.etExpEnd)
        val etDuration: EditText = itemView.findViewById(R.id.etExpDuration)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveExperience)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_experience, parent, false)
        return ExperienceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val exp = experiences[position]

        holder.etInstitute.setText(exp.institute)
        holder.etStart.setText(exp.startDate)
        holder.etEnd.setText(exp.endDate)
        holder.etDuration.setText(exp.duration)

        holder.etInstitute.addTextChangedListener { exp.institute = it.toString() }
        holder.etStart.addTextChangedListener { exp.startDate = it.toString() }
        holder.etEnd.addTextChangedListener { exp.endDate = it.toString() }
        holder.etDuration.addTextChangedListener { exp.duration = it.toString() }

        holder.btnRemove.setOnClickListener {
            experiences.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, experiences.size)
        }
    }

    override fun getItemCount(): Int = experiences.size
}
