package com.example.myapplicationb1203288

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class chat : AppCompatActivity() {

    // 宣告所有在 activity_chat.xml 中帶有 ID 的視圖元件變數
    // 使用 lateinit var 表示會在使用前被初始化
    private lateinit var btnBackChat: ImageButton
    private lateinit var tvChatTitle: TextView
    private lateinit var btnMenuChat: ImageButton
    private lateinit var tvRestaurantInfo: TextView

    private lateinit var btnQuickAskOpen: Button
    private lateinit var btnQuickBookTable: Button
    private lateinit var btnCamera: ImageButton
    private lateinit var btnGallery: ImageButton
    private lateinit var etMessageInput: EditText
    private lateinit var btnEmoji: ImageButton
    private lateinit var btnSend: ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化所有視圖元件，將 XML 佈局中的 ID 與 Kotlin 變數綁定
        btnBackChat = findViewById(R.id.btn_back_chat)
        tvChatTitle = findViewById(R.id.tv_chat_title)
        btnMenuChat = findViewById(R.id.btn_menu_chat)
        tvRestaurantInfo = findViewById(R.id.tv_restaurant_info)
        // chatContentArea = findViewById(R.id.chat_content_area) // 如果需要，取消註解

        btnQuickAskOpen = findViewById(R.id.btn_quick_ask_open)
        btnQuickBookTable = findViewById(R.id.btn_quick_book_table)
        btnCamera = findViewById(R.id.btn_camera)
        btnGallery = findViewById(R.id.btn_gallery)
        etMessageInput = findViewById(R.id.et_message_input)
        btnEmoji = findViewById(R.id.btn_emoji)
        btnSend = findViewById(R.id.btn_send)

        // --- 1. 左上角的 "<" (返回) 按鈕功能：回到首頁 ---
        btnBackChat.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // --- 2. 按下面灰色的輸入框會跳出鍵盤 ---
        // EditText 本身在點擊時就會自動獲得焦點並彈出鍵盤。
        etMessageInput.setOnClickListener {
            etMessageInput.requestFocus() // 確保輸入框獲得焦點
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // 顯示軟鍵盤
            imm.showSoftInput(etMessageInput, InputMethodManager.SHOW_IMPLICIT)
        }

        // --- 3. 按相機會開啟拍照功能 ---
        btnCamera.setOnClickListener {
            // 創建一個 Intent 來啟動系統相機應用程式
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 檢查是否有應用程式可以處理這個 Intent
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivity(cameraIntent)
            } else {
                // 如果沒有相機應用程式，可以顯示一個 Toast 提示用戶
                // import android.widget.Toast
                Toast.makeText(this, "找不到相機應用程式", Toast.LENGTH_SHORT).show()
                println("No camera app found to handle the intent.") // 輸出到 Logcat
            }
        }

        // 圖片庫按鈕的點擊事件 (用於選擇圖片)
        btnGallery.setOnClickListener {
            // 創建一個 Intent 來啟動系統圖片選擇器
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // 檢查是否有應用程式可以處理這個 Intent
            if (galleryIntent.resolveActivity(packageManager) != null) {
                startActivity(galleryIntent)
            } else {
                Toast.makeText(this, "找不到圖片庫應用程式", Toast.LENGTH_SHORT).show()
                println("No gallery app found to handle the intent.") // 輸出到 Logcat
            }
        }

        // 發送按鈕的點擊事件
        btnSend.setOnClickListener {
            val message = etMessageInput.text.toString().trim() // 獲取輸入框內容並去除前後空格
            if (message.isNotEmpty()) { // 檢查訊息是否為空
                // TODO: 在這裡處理發送訊息的邏輯
                // 例如：
                // 1. 將訊息添加到聊天介面（通常是 RecyclerView 或 ListView）
                //    可以創建一個新的 TextView 或自定義 View 來顯示訊息
                //    println("發送訊息: $message") // 打印到 Logcat
                // 2. 清空輸入框
                etMessageInput.text.clear()
                // 3. (可選) 隱藏軟鍵盤
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etMessageInput.windowToken, 0)
            }
        }

        // 表情符號按鈕的點擊事件
        btnEmoji.setOnClickListener {
            // TODO: 在這裡實現顯示表情符號選擇器或切換到表情符號鍵盤的邏輯
            // 這通常涉及到第三方庫或自定義視圖
            Toast.makeText(this, "開啟表情符號選擇器", Toast.LENGTH_SHORT).show()
            println("Emoji button clicked.")
        }

        // 快捷按鈕的點擊事件 - "請問今天有營業嗎？"
        btnQuickAskOpen.setOnClickListener {
            // 將按鈕的文字設定到輸入框中
            etMessageInput.setText(btnQuickAskOpen.text)
            // 確保輸入框獲得焦點並彈出鍵盤
            etMessageInput.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etMessageInput, InputMethodManager.SHOW_IMPLICIT)
        }

        // 快捷按鈕的點擊事件 - "我想要訂位"
        btnQuickBookTable.setOnClickListener {
            // 將按鈕的文字設定到輸入框中
            etMessageInput.setText(btnQuickBookTable.text)
            // 確保輸入框獲得焦點並彈出鍵盤
            etMessageInput.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etMessageInput, InputMethodManager.SHOW_IMPLICIT)
        }

        // 右上角菜單按鈕的點擊事件
        btnMenuChat.setOnClickListener {
            // TODO: 在這裡實現顯示一個PopupMenu或導航到聊天設定/詳細資訊頁面的邏輯
            Toast.makeText(this, "開啟聊天菜單", Toast.LENGTH_SHORT).show()
            println("Menu button clicked.")
        }
    }
}