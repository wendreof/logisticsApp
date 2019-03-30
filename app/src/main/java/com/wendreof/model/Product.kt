package com.wendreof.model

data class Product(
    val id: Int =0,
    val remetente: String,
    val recebedor: String,
    val descricao: String,
    val assinatura: String,
    val local: String,
    val dataRecebimento: String,
    val codBarras: String,
    val quantidade: String
)