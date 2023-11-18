package com.example.dearmydiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dearmydiary.SQLlight.DatabaseHelper
import com.example.dearmydiary.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val myDB = DatabaseHelper(this)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)

        //시스템에 있는 액션바에 업버튼 붙이기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.buttonInsert.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPwd.text.toString()
            // 데이터를 추가해서 + 화면이동

                if (myDB?.checkLogin(email, password) == true) {
                    // 마이페이지로 이동
                    val userInfoEditor = getSharedPreferences("userInfo", MODE_PRIVATE).edit()
                    userInfoEditor.putString("email", email)
                    userInfoEditor.putString("name", password)
                    userInfoEditor.apply()

                    val intent = Intent(this@LoginActivity, MyPageActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("name",password)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_LONG).show()
                }
            }
        }
//            if (myDB.checkLogin(email, password)) {
//                // 로그인 성공 시 사용자 정보 가져오기
//                val user = myDB.getUserInfoByEmailAndPassword(email, password)
////                val pref = getSharedPreferences("userInfo2", MODE_PRIVATE)
////                val name = pref.getString("name", "")
////                val phone = pref.getString("phone", "")
////                val address = pref.getString("address", "")
////                val filePath = pref.getString("fileUrl", "")
//
//                if (user != null) {
//                // userInfo에 로그인한 사용자 정보 저장
//                    val userInfoEditor = getSharedPreferences("userInfo", MODE_PRIVATE).edit()
//                    userInfoEditor.putString("email", email)
//                    userInfoEditor.putString("name", user.name)
//                    userInfoEditor.putString("phone", user.phone)
//                    userInfoEditor.putString("address", user.address)
//                    userInfoEditor.putString("fileUrl", user.profileUri)
//                    userInfoEditor.apply()
//                    // 마이페이지로 이동
//                    val intent = Intent(this@LoginActivity, MyPageActivity::class.java)
//                    intent.putExtra("email", email)
//                    intent.putExtra("name", user.name)
//
//                // 다른 사용자 정보도 필요하다면 추가하기
//                startActivity(intent)
//
//
//                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
//            }
//        }}}


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
