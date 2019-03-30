package com.wendreof.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.service.WebClient
import kotlinx.android.synthetic.main.form.view.*

class AddDialog(private val viewGroup: ViewGroup,
                private val context: Context) {

    fun show(created: (created: Product) -> Unit) {
        val createdView = LayoutInflater.from(context)
            .inflate(
                R.layout.form,
                viewGroup,
                false
            )

        //Show Dialog
        AlertDialog.Builder(context)
            .setTitle("Adicionar Produto")
            .setView(createdView)
            .setPositiveButton("Save") { _, _ ->
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
                WebClient().insert(product, {
                    created(it)
                }, {
                    Toast.makeText(context, "Falha ao salvar produto", Toast.LENGTH_LONG).show()
                })
            }
            .show()
    }
}

