package com.dabyz.greengrocery1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : Fragment(R.layout.fragment_signup) {
    private val main by lazy { activity as MainActivity }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        main.txFragmentTitle.text = "Nueva Tienda"
        btnSignUp.setOnClickListener {
            //TODO("Form validations and save in firebase")
            main.getSharedPreferences("dabyz.greengrocery", Context.MODE_PRIVATE)?.edit()
                ?.apply { putString("mail", etMail.text.toString()); commit() }
            main.storeModel.addBusiness(
                Business(
                    etName.text.toString(), etMail.text.toString(), etPassword.text.toString(),
                    etPhone.text.toString(), etAddress.text.toString()
                )
            )
            main.supportFragmentManager.beginTransaction()
                ?.apply { replace(R.id.flFragment, ProductsFragment(null)); commit() }
        }
        btnLogIn.setOnClickListener {
            Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }
}