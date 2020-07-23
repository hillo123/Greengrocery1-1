package com.dabyz.greengrocery1

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : Fragment(R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnSignUp.setOnClickListener {
            //TODO("Form validations and save in firebase")
            activity?.getSharedPreferences("dabyzPref", Context.MODE_PRIVATE)?.edit()
                ?.apply { putString("mail", etMail.text.toString()); commit() }
            activity?.supportFragmentManager?.beginTransaction()
                ?.apply { replace(R.id.flFragment, ProductsFragment()); commit() }
        }
        btnLogIn.setOnClickListener {
            Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }
}