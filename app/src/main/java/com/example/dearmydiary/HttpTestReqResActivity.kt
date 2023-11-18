package com.example.dearmydiary

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dearmydiary.adapter.MyAdapterRetrofit
import com.example.dearmydiary.databinding.ActivityHttpTestReqResBinding
import com.example.dearmydiary.model.UserListModel
import com.example.dearmydiary.retrofit.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HttpTestReqResActivity : AppCompatActivity() {
    lateinit var binding: ActivityHttpTestReqResBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHttpTestReqResBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // reqres.in : 무료 rest테스트 서버
        // 데이터를 받아서 리사이클러뷰에 출력
        // 준비물 1) 어댑터 2) 뷰홀더 3) 목록요소의 뷰 4)데이터

        // http통신하기위해
        // 설정관련파일 MyApplication , 2)인터페이스 3) 모델 또는 모델이담겨진리스트

//        {
//            "page": 2,
//            "per_page": 6,
//            "total": 12,
//            "total_pages": 2,

        //키 : data 배열 , 요소 [모델1,모델2,모델3,...]
//            "data": [
//            {
        //모델 : DTO ,VO
//                "id": 7,
//                "email": "michael.lawson@reqres.in",
//                "first_name": "Michael",
//                "last_name": "Lawson",
//                "avatar": "https://reqres.in/img/faces/7-image.jpg"
//            },
        // 결론 :
        // 1)모델이 필요함 .
        // 2)모델을 요소로 하는 리스트 준비

        // 작업순서 5)리사이클러뷰에 넣는작업 , 재사용
        // 데이터 가져오기 1)
        // 우리가 사용하는 MyApplication 으로 형변환
        // applicationContext 안에 우리가 등록한 설정이 있고
        // 형변환된 인스턴스 내부에 networkService를 사용하기
        val networkService = (applicationContext as MyApplication).networkService
        // 데이터 가져오기 2)호출하는 함수콜
        // 우리가 만든 인터페이스에 등록된 추상함수를 이용함 , 인자값은 페이지번호 정의를 문자열타입으로 했다
        val userListCall = networkService.doGetUserList("2")
        //실제 통신이 시작되는 부분. 이함수를 통해서 데이터를 받아온다
        // Callback: retrofit2 :UserListModel 을 기지고있는 배열을 받아온다
        userListCall.enqueue(object : Callback<UserListModel> {
            // 익명클래스 Callback 이라는 레트로핏2에서 제공하는 인터페이스를 구현
            // 반드시 재정의해야하는 함수

           // 리사이클러뷰의 레이아웃 정하는부분.
            // 기본인 LinearLayoutManager 이용
            // 리사이클러 어댑터연결
            // 인자 값은 this@HttpTestReqResActivity
            // 2번째 인자값은 데이터, 네트워크, 레트로핏2 통신으로 받아온 데이터
            override fun onResponse(call: Call<UserListModel>, response: Response<UserListModel>) {
                // 데이터를 성공적으로 받았을때 수행되는 함수
                // 데이터가 response 라는 응답객체에 담겨져서 body에있는내용을 유저리스트에 받음
                val userList = response.body()
                Log.d("syy", "userList의 값:${userList?.data}")
                // 데이터를 성공적으로 받았다면 여기서 리사이클러 뷰 어댑터에 연결하면됨
                val layoutManager =LinearLayoutManager(this@HttpTestReqResActivity)
                binding.retrofitRecyclerView.layoutManager=layoutManager
                binding.retrofitRecyclerView.adapter=MyAdapterRetrofit(this@HttpTestReqResActivity,userList?.data)

            }

            override fun onFailure(call: Call<UserListModel>, t: Throwable) {
                // 데이터를 못받았을때 수행되는 함수
                call.cancel()
            }
        })

    }
}