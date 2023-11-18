package com.example.dearmydiary

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.dearmydiary.SQLlight.DatabaseHelper
import com.example.dearmydiary.databinding.ActivityJoinBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    lateinit var filePath: String

    var myDB: DatabaseHelper? = null


    var userEmail: EditText? = null
    var userName: EditText? = null
    var userPwd: EditText? = null
    var userPhone: EditText? = null
    var userAddress: EditText? = null
    var buttonInsert: Button? = null
    var buttonView: Button? = null
    var buttonUpdate: Button? = null
    var buttonDelete: Button? = null
    var editTextID: EditText? = null


//    var buttonUpdate: Button? = null
//    var buttonDelete: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDB = DatabaseHelper(this)
        userEmail = binding.userEmail
        userName = binding.userName
        userPwd = binding.userPwd
        userPhone = binding.userPhone
        userAddress = binding.userAddress
        buttonInsert = binding.buttonInsert
        buttonView = binding.buttonView
        buttonUpdate = binding.buttonUpdate
        buttonDelete = binding.buttonDelete
        editTextID = binding.editTextID

        AddData()
        ViewAll()
        UpdateData()
        DeleteData()

        // 툴바 붙이기
        setSupportActionBar(binding.toolbar)
        //시스템에 있는 액션바에 업버튼 붙이기
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.profile_img_width),
                    resources.getDimensionPixelSize(R.dimen.profile_img_height),
                )
                val options = BitmapFactory.Options()
                options.inSampleSize = calRatio

                var inputStream = contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream!!.close()
                inputStream = null
                binding.resultUserImage.setImageBitmap(bitmap)
                Log.d("syy", "갤러리에서 선택된 사진의 크기 비율 calRatio : $calRatio")

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("syy", "사진 출력 실패")

            }

        }

        binding.galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)

        }


        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.profile_img_width),
                resources.getDimensionPixelSize(R.dimen.profile_img_height),
            )
            val options = BitmapFactory.Options()
            options.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, options)
            binding.resultUserImage.setImageBitmap(bitmap)


        }

        binding.cameraBtn.setOnClickListener {

            //파일 이름 준비하기.
            val timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val file = File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )

            filePath = file.absolutePath
            Log.d("syy", "file.absolutePath : $filePath")

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.example.dearmydiary.fileprovider",
                file
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestCameraFileLauncher.launch(intent)

        }


    }

    //데이터베이스 수정하기
    fun UpdateData() {
        buttonUpdate!!.setOnClickListener {
            val isUpdated = myDB!!.updateData(
                editTextID!!.text.toString(),
                userEmail!!.text.toString(),
                userName!!.text.toString(),
                userPwd!!.text.toString(),
                userPhone!!.text.toString(),
                userAddress!!.text.toString(),
                filePath
            )
            if (isUpdated == true)
                Toast.makeText(this@JoinActivity, "데이터 수정 성공", Toast.LENGTH_LONG)
                    .show()
            else Toast.makeText(this@JoinActivity, "데이터 수정 실패", Toast.LENGTH_LONG)
                .show()
        }
    }  // 데이터베이스 읽어오기
    fun ViewAll() {
        buttonView!!.setOnClickListener(View.OnClickListener {
            // res에 조회된 , 테이블의 내용이 들어가 있다. select 의 조회의 결괏값있다.
            // res -> Cursor
            val res = myDB!!.allData
            // 결과가 없을 때
            if (res.count == 0) {
                ShowMessage("실패", "데이터를 찾을 수 없습니다.")
                return@OnClickListener
            }
            val buffer = StringBuffer()
            while (res.moveToNext()) {
                buffer.append(
                    """
       ID: ${res.getString(0)}

    """.trimIndent()
                )
                buffer.append(
                    """
    이메일: ${res.getString(1)}

    """.trimIndent()
                )
                buffer.append(
                    """
    이름: ${res.getString(2)}

    """.trimIndent()
                )

                buffer.append(
                    """
    비밀번호: ${res.getString(3)}

    """.trimIndent()
                )

                buffer.append(
                    """
    전화번호: ${res.getString(4)}

    """.trimIndent()
                )
                buffer.append(
                    """
    주소: ${res.getString(5)}


    """.trimIndent()
                )
            }
            ShowMessage("회원목록", buffer.toString())
        })
    }

        fun AddData() {
            buttonInsert!!.setOnClickListener {
                val isInserted = myDB!!.insertData(
                    userEmail!!.text.toString(),
                    userName!!.text.toString(),
                    userPwd!!.text.toString(),
                    userPhone!!.text.toString(),
                    userAddress!!.text.toString(),
                    filePath
                )

                if (isInserted == true) {

                    Toast.makeText(this@JoinActivity, "데이터추가 성공", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@JoinActivity, "데이터추가 실패", Toast.LENGTH_LONG).show()
                }
            }
        }

        // 데이터베이스 삭제하기
        fun DeleteData() {
            buttonDelete!!.setOnClickListener {
                val deleteRows = myDB!!.deleteData(userEmail!!.text.toString())
                if (deleteRows > 0)
                    Toast.makeText(this@JoinActivity, "데이터 삭제 성공", Toast.LENGTH_LONG)
                        .show()
                else Toast.makeText(this@JoinActivity, "데이터 삭제 실패", Toast.LENGTH_LONG)
                    .show()
            }
        }


    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            // 변환 작업.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("lsy", "사진 크기 비율 계산 실패 ")
        }
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth
            ) {
                inSampleSize *= 2
            }
        }

        return inSampleSize

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

    fun ShowMessage(title: String?, Message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }
}
