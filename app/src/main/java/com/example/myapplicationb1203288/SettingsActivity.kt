package com.example.myapplicationb1203288

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {

    // 宣告所有會用到的視圖元件變數
    private lateinit var btnBack: ImageButton
    private lateinit var switchDarkMode: SwitchCompat
    private lateinit var darkModeSetting: ConstraintLayout // 整個深色模式的佈局
    private lateinit var cardAboutUs: CardView
    private lateinit var cardPrivacyPolicy: CardView
    private lateinit var languageSetting: ConstraintLayout // 整個語言設定的佈局

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 初始化視圖元件：使用 findViewById() 找到對應的視圖
        btnBack = findViewById(R.id.btn_back)
        switchDarkMode = findViewById(R.id.switch_dark_mode)
        darkModeSetting = findViewById(R.id.dark_mode_setting)
        cardAboutUs = findViewById(R.id.card_about_us)
        cardPrivacyPolicy = findViewById(R.id.card_privacy_policy)
        languageSetting = findViewById(R.id.language_setting)
        // tvLanguageValue = findViewById(R.id.tv_language_value) // 如果需要，請取消註解

        // 1. "< 返回 " 按鈕功能：回到首頁或上一個頁面
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 2. 深色模式切換
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        switchDarkMode.isChecked = (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES ||
                (currentNightMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM &&
                        resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES))

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            recreate() // 重建 Activity 以應用新主題。
        }

        // 讓點擊整個深色模式的 ConstraintLayout 來觸發切換
        darkModeSetting.setOnClickListener {
            switchDarkMode.toggle() // 切換開關的狀態
        }

        // 3. 按到關於我們時，會跳到另外一個頁面
        //cardAboutUs.setOnClickListener {
        //    val intent = Intent(this, AboutWeActivity::class.java) //連結分組部分
        //    startActivity(intent)
        //}

        // 隱私條款按鈕的點擊事件
        cardPrivacyPolicy.setOnClickListener {
            val privacyPolicyUrl = "https://yourwebsite.com/privacy" // <-- 請替換為您的實際隱私條款網址
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
                startActivity(browserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 語言設定的點擊事件
        languageSetting.setOnClickListener {
            try {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    // 輔助函式：獲取當前語言的顯示名稱 (用於語言顯示)
    private fun getCurrentLanguageDisplayName(): String {
        return when (resources.configuration.locales.get(0).language) {
            "zh" -> {
                when (resources.configuration.locales.get(0).script) {
                    "Hans" -> "简体中文" // 簡體
                    else -> "繁體中文" // 預設繁體 (包括 Hant 或未指定)
                }
            }

            "en" -> "English"
            else -> "繁體中文" // 預設值
        }
    }
}