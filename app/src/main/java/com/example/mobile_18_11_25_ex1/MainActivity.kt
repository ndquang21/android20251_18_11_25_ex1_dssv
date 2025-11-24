package com.example.mobile_18_11_25_ex1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edtHoTen: EditText
    private lateinit var edtMSSV: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView

    // Danh sách sinh viên
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    // Biến lưu vị trí sinh viên đang được chọn để sửa (-1 là chưa chọn ai)
    private var currentEditingIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ View
        edtHoTen = findViewById(R.id.edtHoTen)
        edtMSSV = findViewById(R.id.edtMSSV)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        // Cấu hình RecyclerView và Adapter
        adapter = StudentAdapter(studentList,
            onUserClick = { student ->
                // Khi click vào 1 sinh viên: Đẩy thông tin lên EditText
                edtHoTen.setText(student.hoten)
                edtMSSV.setText(student.mssv)
                // Lưu lại vị trí đang sửa
                currentEditingIndex = studentList.indexOf(student)
            },
            onDeleteClick = { student ->
                // Khi click nút xóa
                studentList.remove(student)
                adapter.notifyDataSetChanged() // Cập nhật lại giao diện
                Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Xử lý nút ADD
        btnAdd.setOnClickListener {
            val hoten = edtHoTen.text.toString()
            val mssv = edtMSSV.text.toString()

            if (hoten.isNotEmpty() && mssv.isNotEmpty()) {
                val newStudent = Student(hoten, mssv)
                studentList.add(newStudent)
                adapter.notifyDataSetChanged() // Cập nhật list

                // Xóa trắng ô nhập
                edtHoTen.text.clear()
                edtMSSV.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút UPDATE
        btnUpdate.setOnClickListener {
            if (currentEditingIndex != -1) { // Kiểm tra xem có đang chọn ai không
                val hotenMoi = edtHoTen.text.toString()
                val mssvMoi = edtMSSV.text.toString()

                // Cập nhật dữ liệu trong list
                studentList[currentEditingIndex].hoten = hotenMoi
                studentList[currentEditingIndex].mssv = mssvMoi

                adapter.notifyDataSetChanged() // Làm mới list

                // Reset trạng thái
                currentEditingIndex = -1
                edtHoTen.text.clear()
                edtMSSV.text.clear()
                Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Chưa chọn sinh viên để sửa", Toast.LENGTH_SHORT).show()
            }
        }
    }
}