package com.example.tutorsworldmobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.models.Qualification

class QualificationAdapter(
    private val qualifications: MutableList<Qualification>
) : RecyclerView.Adapter<QualificationAdapter.QualificationViewHolder>() {

    inner class QualificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etInstitute: EditText = itemView.findViewById(R.id.etInstitute)
        val etDegree: EditText = itemView.findViewById(R.id.etDegree)
        val etPassingYear: EditText = itemView.findViewById(R.id.etPassingYear)
        val etPercentage: EditText = itemView.findViewById(R.id.etPercentage)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveQualification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_qualification, parent, false)
        return QualificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: QualificationViewHolder, position: Int) {
        val qualification = qualifications[holder.adapterPosition]

        // Remove existing listeners to prevent "ghost" data updates
        holder.etInstitute.text = null
        holder.etInstitute.append(qualification.institute)

        // Use a simple listener that checks if the field has focus to avoid loop updates
        holder.etInstitute.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) qualification.institute = holder.etInstitute.text.toString()
        }

        holder.etPercentage.text = null
        holder.etPercentage.append(qualification.percentage)

        // Use a simple listener that checks if the field has focus to avoid loop updates
        holder.etPercentage.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) qualification.percentage = holder.etPercentage.text.toString()
        }

        holder.etDegree.text = null
        holder.etDegree.append(qualification.degree)

        // Use a simple listener that checks if the field has focus to avoid loop updates
        holder.etDegree.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) qualification.degree = holder.etDegree.text.toString()
        }

        holder.etPassingYear.text = null
        holder.etPassingYear.append(qualification.passingYear)

        // Use a simple listener that checks if the field has focus to avoid loop updates
        holder.etPassingYear.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) qualification.passingYear = holder.etPassingYear.text.toString()
        }

        // ... Repeat for other fields ...

        holder.btnRemove.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (qualifications.size > 1) { // Optional: don't allow removing the last one
                qualifications.removeAt(currentPos)
                notifyItemRemoved(currentPos)
                notifyItemRangeChanged(currentPos, qualifications.size)
            } else {
                Toast.makeText(holder.itemView.context, "At least one qualification required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = qualifications.size
}
