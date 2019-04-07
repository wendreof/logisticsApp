package com.wendreof.ui.activity

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.wendreof.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.String.format

class SplashActivity : AppCompatActivity()
{
    private var myClipboard: ClipboardManager? = null
    private var myClip: ClipData? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
            showMSG("Obtendo a localização exata. Por favor, aguarde!")
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
            showMSG("Busca pela localização concluída com sucesso!")
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

    fun next(v: View)
    {
        val w = Intent( applicationContext, ListActivity::class.java )
        startActivity( w )
    }

    private fun showMSG( msg: String ) = Snackbar.make( splashActivity, msg, Snackbar.LENGTH_LONG ).show()
}