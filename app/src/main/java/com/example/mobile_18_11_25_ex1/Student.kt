package com.example.mobile_18_11_25_ex1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var hoten: String,
    var mssv: String,
    var soDienThoai: String,
    var diaChi: String
) : Parcelable