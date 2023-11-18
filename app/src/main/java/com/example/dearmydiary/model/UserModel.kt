package com.example.dearmydiary.model

import com.google.gson.annotations.SerializedName

// 가져오는 데이터 타입을 조사 .

//모델 : DTO ,VO
//                "id": 7,
//                "email": "michael.lawson@reqres.in",
//                "first_name": "Michael",
//                "last_name": "Lawson",
//                "avatar": "https://reqres.in/img/faces/7-image.jpg"
//            },
data class UserModel(
    val id:String,
    val email:String,
    @SerializedName("first_name")
    val firstName:String,
//first_name => firstName 으로 저장됨
    @SerializedName("last_name")
    val lastName:String,
    //프로필이미지가 저장된 위치의 URL주소
    val avatar:String,

    // 추가로 속성값 더 가져오기 테스트
)
