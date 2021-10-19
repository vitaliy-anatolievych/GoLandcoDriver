package com.golandco.golandcodriver.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.golandco.golandcodriver.R
import com.golandco.golandcodriver.databinding.ActivityMapsBinding
import com.golandco.golandcodriver.managers.mapstyle.MapStyleManager
import com.golandco.golandcodriver.managers.mapstyle.MapStyles

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mapStylesManager = MapStyleManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setSupportActionBar(binding.layoutNavigation.toolbarContent.toolbar)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val skyMall = LatLng(50.49456747309024, 30.560272889957194)
        map.addMarker(MarkerOptions().position(skyMall).title("Marker in SkyMall"))
        map.moveCamera(CameraUpdateFactory.newLatLng(skyMall))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(skyMall, 16f))
        map.uiSettings.apply {
            this.isMapToolbarEnabled = false
        }
        mapStylesManager.setMapStyle(map, MapStyles.STYLE_RETRO)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val button = binding.layoutNavigation.toolbarContent.toolbar.menu.findItem(R.id.item_map_mode)

        when(item.itemId) {
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                button.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
            }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                button.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
            }
            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                button.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
            }
            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                button.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
            }
        }
        return true
    }
}