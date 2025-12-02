package com.example.mobile_18_11_25_ex1

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()
    private var editingPosition: Int = -1

    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("new_student", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra("new_student")
            }
            newStudent?.let {
                studentList.add(it)
                adapter.notifyItemInserted(studentList.size - 1)
            }
        }
    }

    private val updateStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("updated_student", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra("updated_student")
            }
            if (updatedStudent != null && editingPosition != -1) {
                studentList[editingPosition] = updatedStudent
                adapter.notifyItemChanged(editingPosition)
                editingPosition = -1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = StudentAdapter(studentList,
            onUserClick = { student ->
                editingPosition = studentList.indexOf(student)
                val intent = Intent(this, StudentDetailActivity::class.java).apply {
                    putExtra("student", student)
                }
                updateStudentLauncher.launch(intent)
            },
            onDeleteClick = { student ->
                val position = studentList.indexOf(student)
                studentList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}