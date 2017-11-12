package com.example.hangr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            startActivity<ViewPeople>()
        }

        createAccount.setOnClickListener {
            startActivity<CreateAccount>()
        }
    }
}
