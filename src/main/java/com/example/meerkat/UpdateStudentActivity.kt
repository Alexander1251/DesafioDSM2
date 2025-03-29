package com.example.meerkat

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var spinnerGrade: Spinner
    private lateinit var spinnerSubject: Spinner
    private lateinit var etFinalGrade: EditText
    private lateinit var btnUpdateStudent: Button
    private lateinit var database: DatabaseReference
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        spinnerGrade = findViewById(R.id.spinnerGrade)
        spinnerSubject = findViewById(R.id.spinnerSubject)
        etFinalGrade = findViewById(R.id.etFinalGrade)
        btnUpdateStudent = findViewById(R.id.btnUpdateStudent)

        studentId = intent.getStringExtra("studentId")
        database = FirebaseDatabase.getInstance().getReference("students")

        if(studentId != null){
            database.child(studentId!!).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val student = snapshot.getValue(Student::class.java)
                    if(student != null){
                        etFirstName.setText(student.firstName)
                        etLastName.setText(student.lastName)
                        // Ajustar la selección del Spinner según el valor guardado
                        setSpinnerSelection(spinnerGrade, student.grade)
                        setSpinnerSelection(spinnerSubject, student.subject)
                        etFinalGrade.setText(student.finalGrade.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@UpdateStudentActivity, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnUpdateStudent.setOnClickListener {
            updateStudent()
        }
    }

    private fun setSpinnerSelection(spinner: Spinner, value: String) {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun updateStudent() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val grade = spinnerGrade.selectedItem.toString()
        val subject = spinnerSubject.selectedItem.toString()
        val finalGradeStr = etFinalGrade.text.toString().trim()

        if(firstName.isEmpty() || lastName.isEmpty() || finalGradeStr.isEmpty()){
            Toast.makeText(this, getString(R.string.invalid_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val finalGrade = finalGradeStr.toDoubleOrNull()
        if(finalGrade == null || finalGrade < 0 || finalGrade > 10){
            Toast.makeText(this, getString(R.string.invalid_grade), Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStudent = Student(studentId!!, firstName, lastName, grade, subject, finalGrade)
        database.child(studentId!!).setValue(updatedStudent).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
