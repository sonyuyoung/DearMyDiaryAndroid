package com.example.dearmydiary

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
//        val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
//        val email = pref.getString("email", "")
//        val name = pref.getString("name", "")
//        val password = pref.getString("password", "")
//        val address = pref.getString("address", "")
//        val phone = pref.getString("phone", "")
//        val imagePath = pref.getString("fileUrl", "")

        // 가져온 정보를 마이페이지 화면에 표시
//        binding.resultEmailSP.text = email
//        binding.resultNameSP.text = name
//        binding.resultPasswordSP.text = password
//        binding.resultAddressSP.text = address
//        binding.resultPhoneSP.text = phone

//
//        binding.resultEmailSP.text = Editable.Factory.getInstance().newEditable(email)
//        binding.resultNameSP.text = Editable.Factory.getInstance().newEditable(name)
//        binding.resultPasswordSP.text = Editable.Factory.getInstance().newEditable(password)
//        binding.resultAddressSP.text = Editable.Factory.getInstance().newEditable(address)
//        binding.resultPhoneSP.text = Editable.Factory.getInstance().newEditable(phone)


        // SharedPreferences에서 사용자 정보 가져오기
        val pref = getSharedPreferences("userInfo", MODE_PRIVATE)

        val email = pref.getString("email", "")
        val name = pref.getString("name", "")
        val password = pref.getString("password", "")
        val address = pref.getString("address", "")
        val phone = pref.getString("phone", "")
        val imagePath = pref.getString("fileUrl", "")

        // 초기 설정 시 EditText에 값 설정

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
            val updatedEmail = binding.resultEmailSP.text.toString()
            val updatedName = binding.resultNameSP.text.toString()
            val updatedPassword = binding.resultPasswordSP.text.toString()
            val updatedAddress = binding.resultAddressSP.text.toString()
            val updatedPhone = binding.resultPhoneSP.text.toString()

            // 가져온 값으로 사용자 정보 업데이트
            val editor = pref.edit()
            editor.putString("email", updatedEmail)
            editor.putString("name", updatedName)
            editor.putString("password", updatedPassword)
            editor.putString("address", updatedAddress)
            editor.putString("phone", updatedPhone)
            editor.apply()

        }
    }}


//        binding.updateBtn.setOnClickListener {
//            // 사용자가 수정한 정보를 가져오기
//            val updatedEmail = binding.updatedEmail.text.toString()
//            val updatedName = binding.updatedName.text.toString()
//            val updatedPassword = binding.updatedPassword.text.toString()
//            val updatedAddress = binding.updatedAddress.text.toString()
//            val updatedPhone = binding.updatedPhone.text.toString()
//
//            // SharedPreferences에 사용자 정보 업데이트
//            val pref = getSharedPreferences("userInfo2", MODE_PRIVATE)
//            val editor = pref.edit()
//            editor.putString("email", updatedEmail)
//            editor.putString("name", updatedName)
//            editor.putString("password", updatedPassword)
//            editor.putString("address", updatedAddress)
//            editor.putString("phone", updatedPhone)
//            editor.apply()
//
//            // 업데이트된 정보를 화면에 표시
//            binding.resultEmailSP.text = updatedEmail
//            binding.resultNameSP.text = updatedName
//            binding.resultPasswordSP.text = updatedPassword
//            binding.resultAddressSP.text = updatedAddress
//            binding.resultPhoneSP.text = updatedPhone
//
//            Toast.makeText(this, "데이터 업데이트 성공", Toast.LENGTH_SHORT).show()
//        }
//            val pref = getSharedPreferences("userInfo2", MODE_PRIVATE)
//            val editor = pref.edit()
//            editor.remove("email")
//            editor.remove("name")
//            editor.remove("password")
//            editor.remove("address")
//            editor.remove("phone")
//            editor.remove("fileUrl")
//
//            editor.commit()}

//            val pref = getSharedPreferences("userInfo2", MODE_PRIVATE)
//            val editor = pref.edit()
//            editor.clear()
//            editor.commit()
//        }

//    fun UpdateData() {
//        updateBtn!!.setOnClickListener {
//            val isUpdated = myDB!!.updateData(
//                editTextID!!.text.toString(),
//                editTextName!!.text.toString(),
//                editTextPhone!!.text.toString(),
//                editTextAddress!!.text.toString()
//            )
//            if (isUpdated == true)
//                Toast.makeText(this@DbTestActivity, "데이터 수정 성공", Toast.LENGTH_LONG)
//                    .show()
//            else Toast.makeText(this@DbTestActivity, "데이터 수정 실패", Toast.LENGTH_LONG)
//                .show()
//        }
//    }
