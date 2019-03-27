package com.wendreof.ui

import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.widget.Toast
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val call = RetrofitInitializer().service().list()
        call.enqueue(object: Callback<List<Product>?> {
            override fun onResponse(call: Call<List<Product>?>?,
                                    response: Response<List<Product>?>?) {
                response?.body()?.let{
                    val products: List<Product> = it
                    configureList(products)
                }
            }

            override fun onFailure(call: Call<List<Product>?>?,
                                   t: Throwable?) {
                Log.e("onFailure error", t?.message)
                //Toast.makeText(applicationContext,"onFailure error", message, Toast.LENGTH_LONG).show
            }
        })
    }


    private fun configureList(products: List<Product>){
        val recyclerView = list_recyclerview
        recyclerView.adapter = ListAdapter(products, this)
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun products(): List<Product> {
        return listOf(
            Product(
                "Wendreo",
                "Bianca",
                "XBOX",
                "ASIOUAIOSUAIO",
                "SJBV",
                "11/11/1993",
                "SDNSKLÇDNASKLÇDASNDKASKLÇ",
                "2"
            ),
            Product(
                "Eloa",
                "Sandra",
                "TV",
                "KKKKKKKKKKKKKKK",
                "VGS",
                "11/11/1993",
                "DASFSDFDFSDF",
                "20"
            )
        )

    }
}
