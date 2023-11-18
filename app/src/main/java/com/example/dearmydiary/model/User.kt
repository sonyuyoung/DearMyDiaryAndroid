package com.example.dearmydiary.model

data class User(
    // 유저모델을 리스트로 담을거닌깐.
    var id: String,
    var email: String,
    var name: String,
    var password: String,
    var phone: String,
    var address: String,
    var profileUri:  String,
) {
    // 여기에 생성자 추가
    constructor(
        id: String,
        email: String,
        name: String,
        password: String,
        phone: String,
        address: String
    ) : this(id, email, name, password, phone, address, "")

}