package com.example.dearmydiary

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dearmydiary.SQLlight.DatabaseHelper
import com.example.dearmydiary.databinding.ActivityMyUpdateDataBinding

class MyUpdateDataActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyUpdateDataBinding
    lateinit var myDB: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDB = DatabaseHelper(this)
        val intent = intent
        val updatedEmail = intent.getStringExtra("email")
        val updatedName = intent.getStringExtra("name")
        val updatedPassword = intent.getStringExtra("password")
        val updatedAddress = intent.getStringExtra("address")
        val updatedPhone = intent.getStringExtra("phone")
        val imagePath = intent.getStringExtra("profileUrl")


        // 업데이트된 정보를 화면에 표시
        binding.resultEmailSP.text = Editable.Factory.getInstance().newEditable(updatedEmail)
        binding.resultNameSP.text = Editable.Factory.getInstance().newEditable(updatedName)
        binding.resultPasswordSP.text = Editable.Factory.getInstance().newEditable(updatedPassword)
        binding.resultAddressSP.text = Editable.Factory.getInstance().newEditable(updatedAddress)
        binding.resultPhoneSP.text = Editable.Factory.getInstance().newEditable(updatedPhone)

        if (!imagePath.isNullOrEmpty()) {
            Log.d("FilePath", "Image File Path: $imagePath")
            // Load the image using Glide
            Glide.with(this)
                .load(imagePath)
                .into(binding.resultImageSP)
        }

        // MyUpdateDataActivity 내부의 수정 버튼 클릭 리스너에서 사용자 정보 업데이트
        binding.updateBtn.setOnClickListener {
            val updatedEmail = binding.resultEmailSP.text.toString()
            val updatedName = binding.resultNameSP.text.toString()
            val updatedPassword = binding.resultPasswordSP.text.toString()
            val updatedAddress = binding.resultAddressSP.text.toString()
            val updatedPhone = binding.resultPhoneSP.text.toString()

            // 데이터베이스 업데이트
            if (myDB.updateUserInfo(updatedEmail, updatedName, updatedPassword, updatedAddress, updatedPhone, imagePath)) {

                // 업데이트 성공
                Toast.makeText(this, "사용자 정보가 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 업데이트 실패
                Toast.makeText(this, "사용자 정보 업데이트에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
}}