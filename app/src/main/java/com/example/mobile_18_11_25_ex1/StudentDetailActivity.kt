package com.example.mobile_18_11_25_ex1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val edtMSSV = findViewById<EditText>(R.id.edtMSSV)
        val edtHoTen = findViewById<EditText>(R.id.edtHoTen)
        val edtSoDienThoai = findViewById<EditText>(R.id.edtSoDienThoai)
        val edtDiaChi = findViewById<EditText>(R.id.edtDiaChi)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        val student = intent.getParcelableExtra<Student>("student")

        if (student != null) {
            edtMSSV.setText(student.mssv)
            edtHoTen.setText(student.hoten)
            edtSoDienThoai.setText(student.soDienThoai)
            edtDiaChi.setText(student.diaChi)
        }

        btnUpdate.setOnClickListener {
            val mssv = edtMSSV.text.toString()
            val hoten = edtHoTen.text.toString()
            val soDienThoai = edtSoDienThoai.text.toString()
            val diaChi = edtDiaChi.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("updated_student", Student(hoten, mssv, soDienThoai, diaChi))
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}