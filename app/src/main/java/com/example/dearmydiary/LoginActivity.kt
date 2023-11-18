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
                // 만약 로그인이 성공하면
                if (myDB?.checkLogin(email, password) == true) {
                // 사용자 정보를 가져온다
                    val loggedInUser = myDB.getUserByEmail(email)
                    // SharedPreferences에 사용자 정보를 저장
                    val userInfoEditor = getSharedPreferences("userInfo", MODE_PRIVATE).edit()
//                    userInfoEditor.putString("email", loggedInUser?.email)
//                    userInfoEditor.putString("name", loggedInUser?.password)
//                    userInfoEditor.apply()
                    loggedInUser?.let {
                        userInfoEditor.putString("id", it.id)
                        userInfoEditor.putString("email", it.email)
                        userInfoEditor.putString("name", it.name)
                        userInfoEditor.putString("password", it.password)
                        userInfoEditor.putString("phone", it.phone)
                        userInfoEditor.putString("address", it.address)
                        userInfoEditor.putString("profileUri", it.profileUri)
                        userInfoEditor.apply()
                    }
                    // 마이페이지로 이동
                    val intent = Intent(this@LoginActivity, MyPageActivity::class.java)
//                    intent.putExtra("email", email)
//                    intent.putExtra("name",password)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_LONG).show()
                }
            }
        }



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
