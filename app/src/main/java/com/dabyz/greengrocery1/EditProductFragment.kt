package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_edit_product.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_product.*

class EditProductFragment(private val product: Product) : Fragment(R.layout.fragment_product) {
    private val main by lazy { activity as MainActivity }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        main.txFragmentTitle.text = "Edición de Producto"
        product.apply {
            include.etTitle.text = title
            include.etTitle2.text = title2
            include.etPrice.text = price.toString()
            Glide.with(main).load(photo).into(include.imgProduct)
        }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(main).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Borrar Producto").setMessage("¿Está seguro que desea borrar este producto?")
                .setPositiveButton("Si") { _, _ -> main.storeModel.removeProduct(product); main.supportFragmentManager.popBackStack() }
                .setNegativeButton("No", null).show()
        }
        btnOk.setOnClickListener {
            //TODO validations
            main.storeModel.updateProduct(
                Product(include.etTitle.text.toString(), etTitle2.text.toString(), etPrice.text.toString().toLong()),
                main.compressImg, product
            )
            main.supportFragmentManager.popBackStack()
        }
        btnCancel.setOnClickListener { main.supportFragmentManager.popBackStack() }
        include.imgProduct.setOnClickListener { main.openCamera(include.imgProduct) }
        //TODO main.compressImg=null when pop Back from Stack (find event on...)
    }
}