package com.dabyz.greengrocery1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment(R.layout.fragment_products) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val storeModel = ViewModelProvider(this).get(StoreModel::class.java)
        var productsAdapter = ProductsListAdapter(activity as Context, storeModel)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productsAdapter
        }
        storeModel.selectedBusiness.observe(activity as LifecycleOwner, Observer {
            productsAdapter.apply { products = it.refs; notifyDataSetChanged() }
        })
        btnOrders.setOnClickListener {
            Toast.makeText(context, "Orders not implemented yet", Toast.LENGTH_SHORT).show()
        }
        btnNewProduct.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.apply { replace(R.id.flFragment, NewProductFragment()); commit() }
        }
    }

    class ProductsListAdapter(val context: Context, val storeModel: StoreModel) : RecyclerView.Adapter<ProductsListAdapter.ItemHolder>() {
        var products = listOf<StoreModel.Product>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListAdapter.ItemHolder =
            ItemHolder(LayoutInflater.from(context).inflate(R.layout.card_product, parent, false))

        override fun getItemCount(): Int = products.size

        override fun onBindViewHolder(holder: ProductsListAdapter.ItemHolder, position: Int) =
            holder.setData(products[position], position)

        inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var product: StoreModel.Product? = null

            init {
                itemView.btnDelete.setOnClickListener {
                    AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Borrar Producto")
                        .setMessage("¿Está seguro que desea borrar este producto?")
                        .setPositiveButton("Si") { _, _ -> storeModel.removeProduct(product!!) }
                        .setNegativeButton("No", null)
                        .show()

                    Log.i("ProductListActivity", "Click  delete")
                }
            }

            fun setData(product: StoreModel.Product?, pos: Int) {
                this.product = product
                product?.let {
                    itemView.txvTitle.text = product.title
                    itemView.txvTitle2.text = product.title2
                    itemView.txvPrice.text = product.price.toString()
                    Glide.with(context).load(product.photo).into(itemView.imgProduct)
                }

            }
        }
    }
}