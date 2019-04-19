package com.wendreof.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wendreof.R
import com.wendreof.model.Product
import com.wendreof.retrofit.service.WebClient
import kotlinx.android.synthetic.main.form.view.*

class Dialog( private val viewGroup: ViewGroup, private val context: Context)
{
    private val createdView = createView()
    private val mRemetente = createdView.form_remetente
    private val mRecebedor = createdView.form_recebedor
    private val mDescricao = createdView.form_descricao
    private val mAssinatura = createdView.form_assinatura
    private val mLocal = createdView.form_local
    private val mDataRecebimento = createdView.form_dataRecebimento
    private val mCodBarras = createdView.form_codBarras
    private val mQuantidade = createdView.form_quantidade

    fun alter(product: Product, altered: (alteredProduct: Product) -> Unit)
    {
        mRemetente.setText(product.remetente)
        mRecebedor.setText(product.recebedor)
        mDescricao.setText(product.descricao)
        mAssinatura.setText(product.assinatura)
        mLocal.setText(product.local)
        mDataRecebimento.setText(product.dataRecebimento)
        mCodBarras.setText(product.codBarras)
        mQuantidade.setText(product.quantidade)

        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.alterar_entrega))
            .setView(createdView)
            .setPositiveButton(context.getString(R.string.Salvar)) { _, _ ->
                val remetente = mRemetente.text.toString()
                val recebedor = mRecebedor.text.toString()
                val descricao = mDescricao.text.toString()
                val assinatura = mAssinatura.text.toString()
                val local = mLocal.text.toString()
                val dataRecebimento = mDataRecebimento.text.toString()
                val codBarras = mCodBarras.text.toString()
                //val quantidade = toInt(mQuantidade.text.toString())
                val quantidade = mQuantidade.text.toString()
                val alteredProduct = product.copy(
                    remetente = remetente,
                    recebedor = recebedor,
                    descricao = descricao,
                    assinatura = assinatura,
                    local = local,
                    dataRecebimento = dataRecebimento,
                    codBarras = codBarras,
                    quantidade = quantidade
                )
                WebClient().alter(alteredProduct, {
                    altered(it)
                }, {
                    Toast.makeText(context, "Falha ao alterar nota", Toast.LENGTH_LONG).show()
                })
            }
            .show()
    }

    fun add(created: (created: Product) -> Unit)
    {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.cadastrar_entrega))
            .setView(createdView)
            .setPositiveButton(context.getString(R.string.Salvar)) { _, _ ->
                val remetente = createdView.form_remetente.text.toString()
                val recebedor = createdView.form_recebedor.text.toString()
                val descricao = createdView.form_descricao.text.toString()
                val assinatura = createdView.form_assinatura.text.toString()
                val local = createdView.form_local.text.toString()
                val dataRecebimento = createdView.form_dataRecebimento.text.toString()
                val codBarras = createdView.form_codBarras.text.toString()
                //val quantidade = toInt(createdView.form_quantidade.text.toString())
                val quantidade = createdView.form_quantidade.text.toString()
                val product = Product(
                    remetente = remetente,
                    recebedor = recebedor,
                    descricao = descricao,
                    assinatura = assinatura,
                    local = local,
                    dataRecebimento = dataRecebimento,
                    codBarras = codBarras,
                    quantidade = quantidade
                )
                WebClient().insert(product, {
                    created(it)
                }, {
                    Toast.makeText(context, context.getString(R.string.falha_ao_cadastrar_entrega), Toast.LENGTH_LONG).show()
                })
            }
            .show()
    }

    private fun createView(): View
    {
        return LayoutInflater.from(context)
            .inflate(R.layout.form, viewGroup, false)
    }

    //private fun toInt(str: String): Int {return toInt(str)}
}

