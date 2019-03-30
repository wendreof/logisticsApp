package com.wendreof.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import android.widget.Toast
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.service.WebClient
import com.wendreof.ui.dialog.Dialog
import com.wendreof.ui.adapter.ListAdapter
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        WebClient().list({
            products.addAll(it)
            configureList()
        },
            {
            Toast.makeText(this, "Falha ao buscar os produtos", Toast.LENGTH_LONG).show()
        })

        fab_add.setOnClickListener {
            Dialog(window.decorView as ViewGroup, this)
                .add {
                    products.add(it)
                    configureList()
                }
        }
    }

    private fun configureList()
    {
        val recyclerView = list_recyclerview
        recyclerView.adapter = ListAdapter(products, this){product, position ->
            Dialog(window.decorView as ViewGroup, this).alter(product){
                products[position] = it
                configureList()
            }
        }
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}


