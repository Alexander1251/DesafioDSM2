package com.example.meerkat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CreateStudentActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var spinnerGrade: Spinner
    private lateinit var spinnerSubject: Spinner
    private lateinit var etFinalGrade: EditText
    private lateinit var btnSaveStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_student)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        spinnerGrade = findViewById(R.id.spinnerGrade)
        spinnerSubject = findViewById(R.id.spinnerSubject)
        etFinalGrade = findViewById(R.id.etFinalGrade)
        btnSaveStudent = findViewById(R.id.btnSaveStudent)

        btnSaveStudent.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val grade = spinnerGrade.selectedItem.toString()
        val subject = spinnerSubject.selectedItem.toString()
        val finalGradeStr = etFinalGrade.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || finalGradeStr.isEmpty()) {
            Toast.makeText(this, "Campos inválidos", Toast.LENGTH_SHORT).show()
            return
        }

        val finalGrade = finalGradeStr.toDoubleOrNull()
        if (finalGrade == null || finalGrade < 0 || finalGrade > 10) {
            Toast.makeText(this, "La nota debe estar entre 0 y 10", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance().getReference("students")
        val studentId = database.push().key

        if (studentId == null) {
            Toast.makeText(this, "Ocurrió un error al generar el ID", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(studentId, firstName, lastName, grade, subject, finalGrade)
        database.child(studentId).setValue(student)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Estudiante guardado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Toast.makeText(this, "Error al guardar: $errorMessage", Toast.LENGTH_LONG).show()
                    Log.e("CreateStudentActivity", "Error al guardar estudiante", task.exception)
                }
            }
    }
}
