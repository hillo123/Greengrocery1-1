package com.dabyz.greengrocery1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var mail = getSharedPreferences("dabyzPref", Context.MODE_PRIVATE).getString("mail", null)
        if (mail == null) {
            supportFragmentManager.beginTransaction()
                .apply { replace(
                    R.id.flFragment,
                    SignUpFragment()
                ); commit() }
        }else{
            supportFragmentManager.beginTransaction()
                .apply { replace(
                    R.id.flFragment,
                    ProductsFragment()
                ); commit() }
        }
    }
}