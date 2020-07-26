package com.dabyz.greengrocery1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class NewProductFragment : Fragment(R.layout.fragment_product) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.txFragmentTitle?.text = "Nuevo Producto"
    }

}