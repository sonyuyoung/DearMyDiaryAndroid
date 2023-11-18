package com.example.dearmydiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dearmydiary.databinding.ActivityMyPageBinding


class MyPageActivity : AppCompatActivity() {
    lateinit var binding :ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // SharedPreferences에서 사용자 정보 가져오기
        val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
        val email = pref.getString("email", "")
        val name = pref.getString("name", "")
        val password = pref.getString("password", "")
        val address = pref.getString("address", "")
        val phone = pref.getString("phone", "")
        val imagePath = pref.getString("profileUri", "")


        binding.resultEmailSP.setText(email)
        binding.resultNameSP.setText(name)
        binding.resultPasswordSP.setText(password)
        binding.resultAddressSP.setText(address)
        binding.resultPhoneSP.setText(phone)


        if (!imagePath.isNullOrEmpty()) {
            Log.d("FilePath", "Image File Path: $imagePath")
            // Load the image using Glide
            Glide.with(this)
                .load(imagePath)
                .into(binding.resultImageSP)
        }

        binding.updateBtn.setOnClickListener {
            val intent = Intent(this@MyPageActivity, MyUpdateDataActivity::class.java)

            // 사용자 정보를 intent에 추가
            intent.putExtra("email", email)
            intent.putExtra("name", name)
            intent.putExtra("password", password)
            intent.putExtra("address", address)
            intent.putExtra("phone", phone)
            intent.putExtra("profileUrl", imagePath)
            // MyUpdateDataActivity 시작
            startActivity(intent)
        }
    }
}
