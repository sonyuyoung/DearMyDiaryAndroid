package com.example.dearmydiary

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.dearmydiary.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)

        //시스템에 있는 액션바에 업버튼 붙이기
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 드로워 화면에 액션 버튼 클릭시 -> 드로워 화면에 나오게.
        toggle = ActionBarDrawerToggle(
            this@HomeActivity,
            binding.drawer, R.string.open, R.string.close
        )
        // 화면에 붙이는 작업, 적용하기.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // 버튼 클릭시, 동기화, 드러워 화면을 열어주는 기능.
        toggle.syncState()


        val navigationView = findViewById<NavigationView>(R.id.main_drawer_view)
        val headerView = navigationView.getHeaderView(0)
        val headerTextView = headerView.findViewById<TextView>(R.id.navView)


        // SharedPreferences에서 로그인 정보 가져오기
        val pref = getSharedPreferences("userInfo", MODE_PRIVATE)
        val userEmail = pref.getString("email", "")

        if (headerTextView == null) {
            Log.e("syy", "headerTextView is null")
        } else {
            headerTextView.text = "${userEmail}님 환영합니다"

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}