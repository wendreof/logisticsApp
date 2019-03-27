package com.wendreof.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.wendreof.R
import com.wendreof.model.Product
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView = list_recyclerview
        recyclerView.adapter = ListAdapter(products(), this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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
