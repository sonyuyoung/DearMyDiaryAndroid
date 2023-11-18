package com.example.dearmydiary.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dearmydiary.databinding.ItemRetrofitBinding
import com.example.dearmydiary.model.UserModel
import com.example.dearmydiary.retrofit.MyApplication
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// 데이터를 받아서 리사이클러뷰에 출력
// 준비물 1) 어댑터 2) 뷰홀더 3) 목록요소의 뷰 4)데이터

// 매개변수에는 목록요소의 뷰가 들어간다
// 이름 : item_retrofit.xml
// 뷰 : 이미지 하나 오른족에 세로방향으로 텍스트뷰 3개 정도
// 뷰홀더 ItemRetrofitBinding
// datas -> [모델1,모델2,모델2,... ]

class MyViewHolderRetrofit(val binding:
                           ItemRetrofitBinding) : RecyclerView.ViewHolder(binding.root)

// 어댑터에 필요한요소 : 매개변수(인자) context , 2) 데이터
// 재정의함수 호출
// 클래스의 주생성자 ( 클래스명뒤에오는 소괄호안의 내용 ) constructor 생략하고 많이씀
// val , var 지정하면 클래스내에 전역으로 사용가능
// 그래서 및에 특정함수 내부에서 쉽게 접근 및 사용이 가능하다 .
// 이거 val context: Context, val datas: List<UserModel>?
class MyAdapterRetrofit(val context: Context, val datas: List<UserModel>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {
        //정의 해놓았던 MyAdapterRetrofit 목록요소를 해당 어댑터에 적용할것임..

        // 고정틀이고 MyViewHolderRetrofit/ItemRetrofitBinding 이런거만 바뀜
        return MyViewHolderRetrofit(ItemRetrofitBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    // 코드 그대로 재사용
    override fun getItemCount(): Int {
        // ? : 널허용 연산자 ?: :엘비스 연산자
        // 해당값이 있으면 , 그값을 가용하고 : data.size 사용하고,
        // 이게만약 널이라고 한다면 엘비스 연산자 ?: 다음의 기본값을 사용
        return datas?.size ?: 0
    }

    // 해당 뷰에 데이터를 연결하는 부분
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 내부에 바인딩을하나더
        // 내부에서 뷰작업 쉽게하려고 뷰홀더로 형변환후 바인딩 재할당
        val binding=(holder as MyViewHolderRetrofit).binding
        // datas에 담겨진 모델을 하나씩 꺼내서 뷰에 데이터를 붙이는 작업을 할거임
        // 리스트의 각요소마다 하나씩 꺼내어 임의의 user 변수에 담기
        val user= datas?.get(position)
        // 뷰에 데이터내용붙이기
        // user 에 email과같음
        binding.retrofitEmail.text=user?.email
        binding.retrofitFirstName.text=user?.firstName
        binding.retrofitLastName.text=user?.lastName

        // 프로필이미지를 가져오기 레트로핏 통신으로 이미지 따로 가져오기
//        val avatarImageCall = (context.applicationContext as MyApplication)
//            .networkService.getAvatarImage(user.avatar)
        // 람다식으로 변환
        // getAvatarImage 들어가보면 url 필요하구나~ user.avatar 안에있음 ..
        // 이미지 가져오는 함수
        val avatarImageCall = user?.let {
            (context.applicationContext as MyApplication)
                .networkService.getAvatarImage(it.avatar)
        }
        // 이미지를 가져오는함수
        // 네트워크 함수 통해서 처리하는부분 방법1)
        // 이미지를 가져오는 통신의 시작은 enqueue()
        // 익명클래스가 Callback 함수구현시 반드시 재정의해야하는 함수 2가지 성공/실패
        avatarImageCall?.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
           // 이미지 가져오기 성공시
               // response . 사진정보가 들어있음.
                if(response.isSuccessful){
                    if(response.body()!=null){
                        // 방법1번
                        var bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
                        binding.retrofitProfileImg.setImageBitmap(bitmap)
                        // 방법2번 39p 18장
                        Glide.with(context)
                            .load(bitmap)
                            .override(100,100)
                            .into(binding.retrofitProfileImg)

                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 이미지 가져오기 실패시
            }

        })

        // 네트워크 함수 통해서 처리하는부분 방법1) ㄲ,ㅌ
        // 빙밥2
//        Glide.with(context)
//            // load 실제 url주소 직접넣기
//            .load(user?.avatar)
//            .override(100,100)
//            .into(binding.retrofitProfileImg)
    }

}