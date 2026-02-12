package com.example.tutorsworldmobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
        val qualification = qualifications[position]

        holder.etInstitute.setText(qualification.institute)
        holder.etDegree.setText(qualification.degree)
        holder.etPassingYear.setText(qualification.passingYear)
        holder.etPercentage.setText(qualification.percentage)

        // Update model when user types
        holder.etInstitute.addTextChangedListener { qualification.institute = it.toString() }
        holder.etDegree.addTextChangedListener { qualification.degree = it.toString() }
        holder.etPassingYear.addTextChangedListener { qualification.passingYear = it.toString() }
        holder.etPercentage.addTextChangedListener { qualification.percentage = it.toString() }

        // Remove item
        holder.btnRemove.setOnClickListener {
            qualifications.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, qualifications.size)
        }
    }

    override fun getItemCount(): Int = qualifications.size
}
