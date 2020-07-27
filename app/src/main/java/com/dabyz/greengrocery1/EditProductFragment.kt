package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_product.*

class EditProductFragment(val product: Product) : Fragment(R.layout.fragment_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.txFragmentTitle?.text = "Edición de Producto"
        product?.let {
            include.etTitle.text = product.title
            include.etTitle2.text = product.title2
            include.etPrice.text = product.price.toString()
            Glide.with(activity!!).load(product.photo).into(include.imgProduct)
        }
        btnCancel.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        btnOk.setOnClickListener { Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show() }
        include.imgProduct.setOnClickListener { Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show() }
    }
}