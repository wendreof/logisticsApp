package com.wendreof.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.TextView
import com.wendreof.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.ByteArrayOutputStream
import java.lang.String.format

class SplashActivity : AppCompatActivity()
{
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null
    private val BARCODE_ACTIVITY = 555

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       // iniciarCamera()

      takePicture.setOnClickListener{tirarFoto()}

        editLocation.isEnabled = false
        editImagem.isEnabled = false

        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
    }

    fun iniciarGPS(v: View)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        }
        else
        {
            solicitarGPS()
            showMSG(getString(R.string.buscando_localizacao))
        }

        btnAllowGPS.isEnabled = false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        when (requestCode)
        {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //solicitarTelefone();
                }
                else
                {
                    showMSG(getString(R.string.without_phone_permission))
                }
                return
            }
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    solicitarGPS()
                }
                else
                {
                    showMSG(getString(R.string.without_gps_permissions))
                }
                return
            }
        }
    }

    fun solicitarGPS()
    {
        try {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val locationListener = object : LocationListener
            {
                override fun onLocationChanged(location: Location)
                {
                    apresentar(location)
                }
                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0f, locationListener )
            showMSG(getString(R.string.busca_localizacao_sucesso))
        }
        catch ( ex: SecurityException )
        {
            showMSG( ex.message.toString() )
        }
    }

    fun apresentar( l: Location )
    {
        val latPoint = l.latitude
        val lngPoint = l.longitude

        editLocation.setText( "" )
        editLocation.setText( format("Lat: %s | Long: %s", latPoint.toString(), lngPoint.toString()) )
    }

    fun copyText(v: View)
    {
        myClip = ClipData.newPlainText("text", editLocation.text )
        myClipboard?.primaryClip = myClip

        showMSG(getString(R.string.location_copied))
    }

    fun copyText2(v: View)
    {
        myClip = ClipData.newPlainText("text", editImagem.text )
        myClipboard?.primaryClip = myClip

        showMSG(getString(R.string.location_copied))
    }

    fun next(v: View)
    {
        val w = Intent( applicationContext, ListActivity::class.java )
        startActivity( w )
    }

    private fun showMSG( msg: String ) = Snackbar.make( splashActivity, msg, Snackbar.LENGTH_LONG ).show()

    fun iniciarCamera()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    fun tirarFoto()
    {
        iniciarCamera()
        val it = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(it, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            val extras = data!!.extras
            val imagem = extras.get("data") as Bitmap
            imageViewPhoto.setImageBitmap(imagem)
            tobase64(imagem)
        }
        else if (requestCode == BARCODE_ACTIVITY && resultCode == Activity.RESULT_OK){
                val tx = findViewById<TextView>(R.id.editBarcode)
                val strValor = data!!.getStringExtra("codigo_barra")
                tx.text = strValor.toString()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun tobase64(bitmap: Bitmap)
    {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)  // BASE 64
        val encoded2 = Base64.encode(byteArray, Base64.DEFAULT)         // ARRAY DE BYTES

        showMSG(encoded2.toString())

        editImagem.setText(encoded2.toString())
    }

    fun btnLeitura_onClick(view: View) {
        val itn = Intent(this, CodeReaderActivity::class.java)
        startActivityForResult(itn, BARCODE_ACTIVITY)
    }
}