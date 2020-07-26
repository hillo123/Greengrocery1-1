package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_product.*

class NewProductFragment : Fragment(R.layout.fragment_new_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.txFragmentTitle?.text = "Nuevo Producto"
        btnCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        btnOk.setOnClickListener{
            Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
        include.imgProduct.setOnClickListener{
            Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }

}