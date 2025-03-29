package com.example.meerkat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class StudentListActivity : AppCompatActivity() {

    private lateinit var rvStudents: RecyclerView
    private lateinit var studentList: MutableList<Student>
    private lateinit var adapter: StudentAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        rvStudents = findViewById(R.id.rvStudents)
        rvStudents.layoutManager = LinearLayoutManager(this)
        studentList = mutableListOf()
        adapter = StudentAdapter(studentList)
        rvStudents.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("students")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()
                for (data in snapshot.children) {
                    val student = data.getValue(Student::class.java)
                    if (student != null) {
                        studentList.add(student)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@StudentListActivity, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
            }
        })
    }

    inner class StudentAdapter(private val students: MutableList<Student>) :
        RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

        inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvStudentInfo: TextView = itemView.findViewById(R.id.tvStudentInfo)
            val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
            val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
            return StudentViewHolder(view)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            val student = students[position]
            holder.tvStudentInfo.text = "${student.firstName} ${student.lastName}\nGrado: ${student.grade}\nMateria: ${student.subject}\nNota Final: ${student.finalGrade}"

            holder.btnEdit.setOnClickListener {
                val intent = Intent(this@StudentListActivity, UpdateStudentActivity::class.java)
                intent.putExtra("studentId", student.id)
                startActivity(intent)
            }

            holder.btnDelete.setOnClickListener {
                database.child(student.id).removeValue().addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@StudentListActivity, "Estudiante eliminado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@StudentListActivity, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return students.size
        }
    }
}
