package com.example.dearmydiary.retrofit

import com.example.dearmydiary.model.UserListModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


// 통신라이브러리 : retrofit2 이용해서
// 인터페이스에 추상메서드 만들어서
// 레트로핏한테 전달: 인터페이스를 통으로 전달하면
// 여기에 정의된 함수를 이용해서 통신을한다
// 기본 CRUD가 일어나는 인터페이스
// @GET, @POST , @PUT , @DELETE , @HEAD
interface INetworkService {
    @GET("api/users")
    //@Query("page")를 하면 , https://reqres.in/users?page=2 이게 호출이 된느거임
    fun doGetUserList(@Query("page") page:String): Call<UserListModel>
    // 예시로 함수를 호출할때 -> doGetUserList("3")
    // 반환타입은 Call이라는 타입이고 담겨진 데이터는 리스트로 받는다.
    // 리스트 요소가 (UserModel) - UserListModel > UserModel
    // => https://reqres.in/users?page=3

    //baseUrl : https://reqres.in/
    //get 가져올 url @GET("users/list")
    //아래의 서버의 주소에서 , 뒷 부분에 추가 되는 부분
    //baseUrl("https://reqres.in/")
    //예시)
    //https://reqres.in/users/list


    // 프로핑이미지를 받기위한 추상메서드,추상함수 . : 선언만있고 본문이없는거
    // @Url 이라는게 기본 baseUrl이 있지만 다른 Url을 호출할때 사용
    @GET
    fun getAvatarImage(@Url url:String):Call<ResponseBody>


}