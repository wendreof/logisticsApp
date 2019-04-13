package com.wendreof.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import com.wendreof.R
import me.dm7.barcodescanner.zxing.ZXingScannerView

class CodeReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler
{

    private var mScannerView: ZXingScannerView? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_reader)
                mScannerView = findViewById(R.id.scanner)
    }

    override fun onResume()
    {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun handleResult(rawResult: Result)
    {
        Log.d("BARCODE", rawResult.text.toString())
        val saida = rawResult.text
        val intent = Intent()
        intent.putExtra("codigo_barra", saida)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onPause()
    {
        super.onPause()
        mScannerView!!.stopCamera()
    }
}
