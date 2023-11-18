package com.example.dearmydiary

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.example.dearmydiary.SQLlight.DatabaseHelper
import com.example.dearmydiary.databinding.ActivityMyUpdateDataBinding

class MyUpdateDataActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyUpdateDataBinding
    lateinit var myDB: DatabaseHelper
    var filePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMyUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDB = DatabaseHelper(this)

//        // SharedPreferences에서 사용자 정보 가져오기
//
//        val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
//        val email = pref.getString("email", "")
//        val name = pref.getString("name", "")
//        val password = pref.getString("password", "")
//        val address = pref.getString("address", "")
//        val phone = pref.getString("phone", "")
//        val imagePath = pref.getString("fileUrl", "")
//
//        // 초기 설정 시 EditText에 값 설정
//        binding.resultEmailSP.setText(email)
//        binding.resultNameSP.setText(name)
//        binding.resultPasswordSP.setText(password)
//        binding.resultAddressSP.setText(address)
//        binding.resultPhoneSP.setText(phone)
//
//            // 가져온 값으로 사용자 정보 업데이트
//            val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
//            val editor = pref.edit()
//            editor.putString("email", updatedEmail)
//            editor.putString("name", updatedName)
//            editor.putString("password", updatedPassword)
//            editor.putString("address", updatedAddress)
//            editor.putString("phone", updatedPhone)
//            editor.apply()
//
//        if (!imagePath.isNullOrEmpty()) {
//            // Glide를 사용하여 이미지 로딩
//            Glide.with(this)
//                .load(imagePath)
//                .into(binding.resultImageSP)
//        }
//
//
//            // EditText에서 값을 가져오기
//            val updatedEmail = binding.resultEmailSP.text.toString()
//            val updatedName = binding.resultNameSP.text.toString()
//            val updatedPassword = binding.resultPasswordSP.text.toString()
//            val updatedAddress = binding.resultAddressSP.text.toString()
//            val updatedPhone = binding.resultPhoneSP.text.toString()
//
//            binding.resultEmailSP.setText(updatedEmail)
//            binding.resultEmailSP.isFocusable = false
//            binding.resultEmailSP.isClickable = false
//
//            binding.resultNameSP.setText(updatedName)
//            binding.resultNameSP.isFocusable = false
//            binding.resultNameSP.isClickable = false
//
//            // 업데이트된 정보를 화면에 표시
//            binding.resultEmailSP.text = Editable.Factory.getInstance().newEditable(updatedEmail)
//            binding.resultNameSP.text = Editable.Factory.getInstance().newEditable(updatedName)
//            binding.resultPasswordSP.text = Editable.Factory.getInstance().newEditable(updatedPassword)
//            binding.resultAddressSP.text = Editable.Factory.getInstance().newEditable(updatedAddress)
//            binding.resultPhoneSP.text = Editable.Factory.getInstance().newEditable(updatedPhone)
//
//            // 데이터베이스 업데이트 함수 호출
//            myDB.updateUserInfo(
//                updatedEmail,
//                updatedName,
//                updatedPassword,
//                updatedAddress,
//                updatedPhone,
//                filePath
//            )
//
//            Toast.makeText(this, "데이터 업데이트 성공", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//}

val updatedEmail = binding.resultEmailSP.text.toString()
val updatedName = binding.resultNameSP.text.toString()
val updatedPassword = binding.resultPasswordSP.text.toString()
val updatedAddress = binding.resultAddressSP.text.toString()
val updatedPhone = binding.resultPhoneSP.text.toString()

// 가져온 값으로 사용자 정보 업데이트
val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
val editor = pref.edit()
editor.putString("email", updatedEmail)
editor.putString("name", updatedName)
editor.putString("password", updatedPassword)
editor.putString("address", updatedAddress)
editor.putString("phone", updatedPhone)
editor.apply()

// 업데이트된 정보를 화면에 표시
binding.resultEmailSP.text = Editable.Factory.getInstance().newEditable(updatedEmail)
binding.resultNameSP.text = Editable.Factory.getInstance().newEditable(updatedName)
binding.resultPasswordSP.text = Editable.Factory.getInstance().newEditable(updatedPassword)
binding.resultAddressSP.text = Editable.Factory.getInstance().newEditable(updatedAddress)
binding.resultPhoneSP.text = Editable.Factory.getInstance().newEditable(updatedPhone)

}}
