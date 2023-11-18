package com.example.dearmydiary.SQLlight

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dearmydiary.model.User


class DatabaseHelper(context: Context?) : SQLiteOpenHelper
    (context, DATABASE_NAME, null, 1) {
    // 1번째 매개변수 context -> this 현재 호출이 된 액티비티를 의미.
    // 2번째 매개변수 데이터베이스 파일명 ->
    // 예) DATABASE_NAME, 클래스 변수로 선언해서 상수로 사용중
    // 4번째 매개변수 , 현재 사용하는 버전을 의미하고,
    // 이버전이 변경이 된다면, 아래에 있는 onUpgrade 함수 재 호출됨.
    // 테이블 항목


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + TABLE_NAME +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, EMAIL TEXT,NAME TEXT,PASSWORD TEXT,PHONE TEXT, ADDRESS TEXT,PROFILEURI TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // 데이터베이스 추가하기 insert C 임
    fun insertData(
        email: String?, name: String?, password: String?,
        phone: String?,
        address: String?, profileUrl: String?
    ): Boolean {
        // 디비 사용시 쓰기, 수정, 삭제 ->writableDatabase 사용함.
        val db = this.writableDatabase
        // execSQL -> 대신에 ContentValues() 를 이용하면
        // SQL  문장 없이, 바로 메서드에 값만 인자로 넣어서
        // 이용하면, 쉽게 insert를 구현가능.
        val contentValues = ContentValues()
        contentValues.put(COL_2, email)
        contentValues.put(COL_3, name)
        contentValues.put(COL_4, password)
        contentValues.put(COL_5, phone)
        contentValues.put(COL_6, address)
        contentValues.put(COL_7, profileUrl)
        val result = db.insert(TABLE_NAME, null, contentValues)
        return if (result == -1L) false else true
    }


    //데이터베이스 항목 읽어오기 Read
    //특정행에 접근하기위한 인스턴스 Cursor
    //execSQL 업데이트에서 여기선 rawQuery
    // allData 조회한값이 테이블 형태로 들어있다 라고
    val allData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }

    fun checkLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        Log.d("DatabaseHelper", "CheckLogin parameters - Email: $email, Password: $password")

        return try {
            val columns = arrayOf(COL_1)
            val selection = "$COL_2 = ? AND $COL_4 = ?"
            val selectionArgs = arrayOf(email, password)

            val count = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null).use {
                it.count
            }

            /*Log.d("DatabaseHelper", "CheckLogin result - Count: $count")*/

            count > 0
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "로그인 검사 중 오류: ${e.message}", e)
            false
        } finally {
            db.close()
        }
    }
    @SuppressLint("Range")
    fun getUserInfoByEmailAndPassword(email: String, password: String): User? {
        val db = this.readableDatabase
        val columns = arrayOf(COL_1, COL_2, COL_3, COL_4, COL_5, COL_6, COL_7)
        val selection = "$COL_2 = ? AND $COL_4 = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)

        try {
            if (cursor.moveToFirst()) {
                return User(
                    id = cursor.getString(cursor.getColumnIndex(COL_1)),
                    email = cursor.getString(cursor.getColumnIndex(COL_2)),
                    name = cursor.getString(cursor.getColumnIndex(COL_3)),
                    password = cursor.getString(cursor.getColumnIndex(COL_4)),
                    phone = cursor.getString(cursor.getColumnIndex(COL_5)),
                    address = cursor.getString(cursor.getColumnIndex(COL_6)),
                    profileUri = cursor.getString(cursor.getColumnIndex(COL_7))
                )
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error fetching user info: ${e.message}", e)
        } finally {
            cursor.close()
            db.close()
        }

        return null
    }
    // 데이터베이스 삭제하기
    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ? ", arrayOf(id))
    }

    @SuppressLint("Range")
    fun getUserProfileImagePath(email: String?): String? {
        val db = this.readableDatabase
        val query = "SELECT $COL_7 FROM $TABLE_NAME WHERE $COL_2 = ?"

        val cursor = db.rawQuery(query, arrayOf(email))
        var profileImagePath: String? = null

        if (cursor.moveToFirst()) {
            profileImagePath = cursor.getString(cursor.getColumnIndex("profileUri"))
        }

        cursor.close()
        db.close()
        return profileImagePath
    }


    //저장
    // 자바(static) , 클래스 변수 -> 클래스명.  접근.
    companion object {
        // DatabaseHelper 클래스명 -> mydb
        // mydb.DATABASE_NAME -> 사용가능.
        const val DATABASE_NAME = "CLASS4.db" // 데이터베이스 명
        const val TABLE_NAME = "join_table4" // 테이블 명

        // 테이블 항목
        const val COL_1 = "ID"
        const val COL_2 = "Email"
        const val COL_3 = "Name"
        const val COL_4 = "Password"
        const val COL_5 = "Phone"
        const val COL_6 = "Address"
        const val COL_7 = "profileUri"


    }
}
