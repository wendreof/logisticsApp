package com.wendreof.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import android.widget.Toast
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.service.WebClient
import com.wendreof.ui.adapter.ListAdapter
import com.wendreof.ui.dialog.Dialog
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_splash.*

class ListActivity : AppCompatActivity()
{
    private val products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        WebClient().list({
            products.addAll(it)
            configureList() },
            {
           Toast.makeText(this, getString(R.string.error_to_load_products), Toast.LENGTH_LONG).show()
                //showMSG(getString(R.string.error_to_load_products))
        })

        fab_add.setOnClickListener {
            Dialog(window.decorView as ViewGroup, this)
                .add {
                    products.add(it)
                    configureList() }
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
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun showMSG( msg: String ) = Snackbar.make( splashActivity, msg, Snackbar.LENGTH_LONG ).show()

}


