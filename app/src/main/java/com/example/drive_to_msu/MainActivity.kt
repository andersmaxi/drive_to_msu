package com.example.drive_to_msu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //*********************************************************************************
        // Add into AndroidManifest.xml file
        //   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        //    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        //**********************************************************************************
        lateinit var fusedlocation : FusedLocationProviderClient
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnmaps : Button = findViewById<Button>(R.id.btn_drivemsu)

        val btngeo  : Button = findViewById<Button>(R.id.btn_geomsu)


        fusedlocation = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
                ),
                5622
            )

        }

        //******************************************************
        //  GPS option - Current location is implicit
        //******************************************************

        btnmaps.setOnClickListener()
        {


            val url = "google.navigation:q=Montclair State University"

            val gmmIntentUri= Uri.parse(url)

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        //**********************************************************************
        //  Direction option - I have to get Origin (latitude,longitude) using
        //                     getCurrentLocation method
        //**********************************************************************

        btngeo.setOnClickListener()
        {
            var CancelToken: CancellationToken?=null


            val task : Task<Location> = fusedlocation.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,CancelToken)


            task.addOnSuccessListener {
                val lat = it.latitude
                val long = it.longitude

                val url = "https://www.google.com/maps/dir/?api=1&origin="+lat.toString()+","+long.toString()+"&destination=Montclair State University+NJ&travelmode=car"
                val gmmIntentUri=Uri.parse(url)

                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")

                startActivity(mapIntent)
            }



        }


    }

    
}