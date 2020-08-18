package com.dabyz.greengrocery1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class Product(var title: String = "", var title2: String = "", var price: Long = 0, var photo: String = "", var fileName: String = "")
data class Business(
    var name: String = "", val mail: String = "", var password: String = "", var phone: String = "", var address: String = "",
    var refs: ArrayList<Product> = ArrayList()
)

class StoreModel : ViewModel() {
    private val dbBusiness = FirebaseFirestore.getInstance().collection("greengrocery")
    private val fbStorage by lazy { FirebaseStorage.getInstance().reference }
    private var mail = "" // TODO: load from local storage
    val selectedBusiness = MutableLiveData<Business>()

    fun init(mail: String) {
        this.mail = mail
        dbBusiness.document(mail).addSnapshotListener { snapshot, e ->
            e?.let { Log.w("Model", "Listen failed.", e); return@addSnapshotListener }
            if (snapshot != null && snapshot.exists()) {
                selectedBusiness.value = snapshot.toObject(Business::class.java)
            } else {
                Log.d("Model", "Current data: null")
            }
        }
    }

    fun addProduct(product: Product, compressImg: ByteArray) = CoroutineScope(IO).launch {
        product.fileName = "" + System.currentTimeMillis()
        product.photo = savePhoto(compressImg, product.fileName)
        dbBusiness.document(mail).update("refs", FieldValue.arrayUnion(product))
    }

    fun updateProduct(product: Product, compressImg: ByteArray?, oldProduct: Product) = CoroutineScope(IO).launch {
        if (compressImg != null) {
            product.fileName = "" + System.currentTimeMillis()
            product.photo = savePhoto(compressImg, product.fileName)
            fbStorage.child(mail + "/" + oldProduct.fileName + ".JPEG").delete()
                .addOnFailureListener { Log.e("StoreModel", "file " + mail + "/" + product.fileName + ".JPEG deleted error", it) }
        } else {
            product.fileName = oldProduct.fileName
            product.photo = oldProduct.photo
        }
        dbBusiness.document(mail).update("refs", FieldValue.arrayUnion(product))
        dbBusiness.document(mail).update("refs", FieldValue.arrayRemove(oldProduct))
            .addOnFailureListener { Log.e("StoreModel", "$oldProduct deleted error", it) }
    }

    private suspend fun savePhoto(compressImg: ByteArray, fileName: String): String =
        suspendCoroutine { cont ->
            val ref = fbStorage.child("$mail/$fileName.JPEG")
            ref.putBytes(compressImg).addOnCompleteListener {
                ref.downloadUrl.addOnCompleteListener() { cont.resume(it.result.toString()) }
            }
        }

    fun removeProduct(product: Product) {
        dbBusiness.document(mail).update("refs", FieldValue.arrayRemove(product))
        fbStorage.child(mail + "/" + product.fileName + ".JPEG").delete()
            .addOnFailureListener { Log.e("StoreModel", "file " + mail + "/" + product.fileName + ".JPEG deleted ok", it) }
    }

    fun addBusiness(business: Business) = runBlocking {
        dbBusiness.document(business.mail).set(business)
    }
}