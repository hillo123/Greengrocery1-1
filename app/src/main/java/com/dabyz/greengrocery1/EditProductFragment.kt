package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_product.*

class EditProductFragment(private val product: Product) : Fragment(R.layout.fragment_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val main = activity as MainActivity
        main.txFragmentTitle.text = "Edición de Producto"
        val storeModel = ViewModelProvider(this).get(StoreModel::class.java)
        product.apply {
            include.etTitle.text = title
            include.etTitle2.text = title2
            include.etPrice.text = price.toString()
            Glide.with(main).load(photo).into(include.imgProduct)
        }
        btnCancel.setOnClickListener { main.supportFragmentManager.popBackStack() }
        btnOk.setOnClickListener { Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show() }
        btnDelete.setOnClickListener {
            AlertDialog.Builder(main).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Borrar Producto").setMessage("¿Está seguro que desea borrar este producto?")
                .setPositiveButton("Si") { _, _ -> storeModel.removeProduct(product); main.supportFragmentManager.popBackStack() }
                .setNegativeButton("No", null).show()
        }
        include.imgProduct.setOnClickListener { Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show() }
    }
}