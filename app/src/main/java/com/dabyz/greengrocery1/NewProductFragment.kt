package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_edit_product.*
import kotlinx.android.synthetic.main.card_edit_product.view.*
import kotlinx.android.synthetic.main.fragment_product.*

class NewProductFragment : Fragment(R.layout.fragment_new_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val main = activity as MainActivity
        val storeModel = ViewModelProvider(this).get(StoreModel::class.java)
        main.txFragmentTitle.text = "Nuevo Producto"
        btnCancel.setOnClickListener { main.supportFragmentManager.popBackStack() }
        btnOk.setOnClickListener {
            main.compressImg?.let {
                storeModel.addProduct(
                    StoreModel.Product(
                        include.etTitle.text.toString(), etTitle2.text.toString(),
                        etPrice.text.toString().toLong(), ""
                    ), main.compressImg!!
                )
            }
            main.supportFragmentManager.popBackStack()
        }
        include.imgProduct.setOnClickListener { main.openCamera(include.imgProduct) }
    }
}



