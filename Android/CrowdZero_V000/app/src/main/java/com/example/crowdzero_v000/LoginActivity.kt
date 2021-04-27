package com.example.crowdzero_v000

import android.app.ActionBar
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import java.lang.RuntimeException

class LoginActivity : AppCompatActivity() {
    lateinit var layout:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        layout = findViewById(R.id.layoutLoginIni)
        var topbar:MaterialToolbar = findViewById(R.id.topAppBar_Login)
        //topbar.navigationIcon = R.drawable.ic_menu_24dp
        topbar.setLogo(R.drawable.outline_menu_white_24)

    }
}