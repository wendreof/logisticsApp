package com.wendreof.ui

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.CallbackResponse
import com.wendreof.retrofit.service.WebClient
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.form.view.*

class ListActivity : AppCompatActivity() {

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        WebClient().list(object : CallbackResponse<List<Product>> {
            override fun success(products: List<Product>) {
                this@ListActivity.products.addAll(products)
                configureList()
            }
        })

        fab_add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val createdView = LayoutInflater.from(this@ListActivity).inflate(
                    R.layout.form,
                    window.decorView as ViewGroup, false
                )

                //Show Dialog
                AlertDialog.Builder(this@ListActivity)
                    .setTitle("Adicionar Produto")
                    .setView(createdView)
                    .setPositiveButton("Save", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            val remetente = createdView.form_remetente.text.toString()
                            val recebedor = createdView.form_recebedor.text.toString()
                            val descricao = createdView.form_descricao.text.toString()
                            val assinatura = createdView.form_assinatura.text.toString()
                            val local = createdView.form_local.text.toString()
                            val dataRecebimento = createdView.form_dataRecebimento.text.toString()
                            val codBarras = createdView.form_codBarras.text.toString()
                            val quantidade = createdView.form_quantidade.text.toString()
                            val product = Product(
                                remetente, recebedor, descricao,
                                assinatura, local, dataRecebimento,
                                codBarras, quantidade
                            )
                            WebClient().insert(product, object : CallbackResponse<Product> {
                                override fun success(product: Product) {
                                }
                            })
                        }
                    })
                    .show()
            }
        })


        /*   private fun products(): List<Product> {
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

           } */
    }

    private fun configureList() {
        val recyclerView = list_recyclerview
        recyclerView.adapter = ListAdapter(products, this)
        val layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.layoutManager = layoutManager
    }
}
