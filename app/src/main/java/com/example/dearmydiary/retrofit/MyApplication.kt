package com.example.dearmydiary.retrofit

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// 자주 사용이될 네트워크 인터페이스를 미리 시스템에 등록할거고
// 메니페스트에 등록해서
// 앱이 실행이되면 해당 myApplication 의 기능이
// 메모리에 등록이 되어서 사용하기가 편하다 .
class MyApplication : Application() {
    // 1)통신에 필요한 인스턴스를 선언및 초기화를 할거고 : 이름 상관없음
    //선언
    val networkService: INetworkService

    // 2)통신할 서버의 URL 주소를 등록할거임
    // 선언
    val retrofit: Retrofit
        //get 이라는 부분을 Builder 패턴으로 설정을 할건데
        // 해당 Retrofit 에서 제공하는 Builder라는 생성자를 이용
        // .할당.할당
        get() = Retrofit.Builder()
            // REST 통신할 상대방 서버
            .baseUrl("https://reqres.in/")
            // 코틀린으로 만들었던 모델 객체를 제이슨 형태로 컨버터
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // init 라는 키워드를 이용해서 초기화 . networkService 할당될거는
    // retrofit에 retrofit 에 create 함수를 이용해서
    // 내부에있는 INetworkService를 레퍼런스 타입형식으로 정의
    // 초기화 할당
    // retrofit 안에 우리가 만들었던 INetworkService 넣으면
    // retrofit 에서 알아서 필요한정보를 조회해주는데 ,
    // 내가 정의한 함수만 해줌
    init {
        networkService = retrofit.create(INetworkService::class.java)
    }
}