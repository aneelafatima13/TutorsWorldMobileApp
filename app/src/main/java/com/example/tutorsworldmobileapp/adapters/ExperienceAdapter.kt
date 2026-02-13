package com.example.tutorsworldmobileapp

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorsworldmobileapp.models.Experience
import java.util.*

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
        // Use holder.adapterPosition to ensure the correct index during removal/updates
        val exp = experiences[holder.adapterPosition]

        // 1. Reset text to prevent recycling bugs
        holder.etInstitute.setText(exp.institute)
        holder.etStart.setText(exp.startDate)
        holder.etEnd.setText(exp.endDate)
        holder.etDuration.setText(exp.duration)

        // 2. Data Persistence (Updates the model as the user types)
        holder.etInstitute.addTextChangedListener { exp.institute = it.toString() }
        holder.etDuration.addTextChangedListener { exp.duration = it.toString() }

        // 3. Date Picker for Start Date
        holder.etStart.setOnClickListener {
            showDatePicker(holder.etStart) { selectedDate ->
                exp.startDate = selectedDate
            }
        }

        // 4. Date Picker for End Date
        holder.etEnd.setOnClickListener {
            showDatePicker(holder.etEnd) { selectedDate ->
                exp.endDate = selectedDate
            }
        }

        // 5. Remove Item
        holder.btnRemove.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                experiences.removeAt(currentPos)
                notifyItemRemoved(currentPos)
                notifyItemRangeChanged(currentPos, experiences.size)
            }
        }
    }

    override fun getItemCount(): Int = experiences.size

    // Helper function to show DatePickerDialog
    private fun showDatePicker(editText: EditText, onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val dateStr = "$day/${month + 1}/$year"
            editText.setText(dateStr)
            onDateSelected(dateStr)
        }

        DatePickerDialog(
            editText.context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}