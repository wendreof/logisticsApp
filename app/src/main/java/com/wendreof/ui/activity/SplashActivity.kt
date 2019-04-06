package com.wendreof.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.wendreof.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.String.format

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        editLocation.setText("Obtendo localização exata...")
        iniciarGPS()

       // loc = findViewById(R.id.editLocation)
        // getLoc = findViewById(R.id.btnGetLocation)
    }

    fun iniciarTelefone() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
        //else
        //  solicitarTelefone();
    }

    fun iniciarGPS() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        } else
            solicitarGPS()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //solicitarTelefone();
                } else {
                    Toast.makeText(this, "Falta permissão Telefone!!!", Toast.LENGTH_LONG).show()
                }
                return
            }
            2 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    solicitarGPS()
                } else {
                    Toast.makeText(this, "Falta permissão GPS!!!", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    /*  public void solicitarTelefone(){
        EditText campoTelefone = (EditText) findViewById(R.id.txtTelefone);

        String telefone = campoTelefone.getText().toString();

        Uri uri = Uri.parse("tel:"+telefone);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);

    } */

    fun solicitarGPS() {

        try {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    apresentar(location)
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

                override fun onProviderEnabled(provider: String) {}

                override fun onProviderDisabled(provider: String) {}
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (ex: SecurityException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }

    }


    fun apresentar(l: Location) {
        val latPoint = l.latitude
        val lngPoint = l.longitude

        editLocation.setText("")
        editLocation.setText(format("Lat: %s | Long: %s", latPoint.toString(), lngPoint.toString()))
        //txtLatitude.setText(latPoint.toString());
        //txtLongitude.setText(lngPoint.toString());
    }

}