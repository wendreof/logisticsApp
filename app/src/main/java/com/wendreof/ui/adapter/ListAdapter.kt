package com.wendreof.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wendreof.R
import com.wendreof.model.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ListAdapter(
    private val products: List<Product>,
    private val context: Context
) : Adapter<ListAdapter.viewHolder>() {

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val product = products[position]
        holder?.let {
            it.bindView(product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(product: Product) {
            val remetente = itemView.item_remetente
            val recebedor = itemView.item_recebedor
            val descricao = itemView.item_descricao
            val assinatura = itemView.item_assinatura
            val local = itemView.item_local
            val dataRecebimento = itemView.item_dataRecebimento
            val codBarras = itemView.item_codBarras
            val quantidade = itemView.item_quantidade

            remetente.text = product.remetente
            recebedor.text = product.recebedor
            descricao.text = product.descricao
            assinatura.text = product.assinatura
            local.text = product.local
            dataRecebimento.text = product.dataRecebimento
            codBarras.text = product.codBarras
            quantidade.text = product.quantidade
        }
    }
}

