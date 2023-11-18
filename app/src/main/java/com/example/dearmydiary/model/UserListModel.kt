package com.example.dearmydiary.model

// data 라는 값의 요소를 모델링을 했다. UserModel
// UserModel 을 요소로갖는 리스트만들기 -> 리스트도 모델화
//            "page": 2,
//            "per_page": 6,
//            "total": 12,
//            "total_pages": 2,

data class UserListModel(
    val page : String ,
    val perPage:String,
    val total :String,
    val totalPages:String,

    // 이름 data 똑같이 해줘야함 : 그 배열이름
    val data: List<UserModel>
)
