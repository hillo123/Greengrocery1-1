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
    private val main by lazy { activity as MainActivity }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val storeModel = main.storeModel
        main.txFragmentTitle.text = "Nuevo Producto"
        btnCancel.setOnClickListener { main.supportFragmentManager.popBackStack() }
        btnOk.setOnClickListener {
            fun validate(): Boolean = main.compressImg != null //TODO validate all inputs
            if (validate()) {
                storeModel.addProduct(
                    Product(include.etTitle.text.toString(), etTitle2.text.toString(), etPrice.text.toString().toLong()),
                    main.compressImg!!
                )
                main.supportFragmentManager.popBackStack()
            } else
                Toast.makeText(context, "Falta la foto", Toast.LENGTH_SHORT).show() // TODO add popup Toast
        }
        include.imgProduct.setOnClickListener { main.openCamera(include.imgProduct) }
    }
    //TODO main.compressImg=null when pop Back from Stack (find event on...)
}



