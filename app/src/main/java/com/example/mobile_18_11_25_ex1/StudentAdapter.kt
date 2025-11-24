package com.example.mobile_18_11_25_ex1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: MutableList<Student>,
    private val onUserClick: (Student) -> Unit, // Sự kiện click vào item
    private val onDeleteClick: (Student) -> Unit // Sự kiện click nút xóa
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    // ViewHolder: Ánh xạ các view trong item_student.xml
    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student) {
            tvHoTen.text = student.hoten
            tvMSSV.text = student.mssv

            // Xử lý khi nhấn nút Xóa
            btnDelete.setOnClickListener {
                onDeleteClick(student)
            }

            // Xử lý khi nhấn vào cả dòng (để sửa)
            itemView.setOnClickListener {
                onUserClick(student)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int = studentList.size
}