package com.golandco.golandcodriver.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.content.ContextCompat
import com.golandco.golandcodriver.R
import com.golandco.golandcodriver.databinding.ActivityMapsBinding
import com.golandco.golandcodriver.managers.mapsearch.MapSearchManager
import com.golandco.golandcodriver.managers.mapstyle.MapStyleManager
import com.golandco.golandcodriver.managers.mapstyle.MapStyles

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMapsBinding

    private val mapStylesManager = MapStyleManager(this)
    private lateinit var mapSearchManager: MapSearchManager

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
        searchView = binding.layoutNavigation.toolbarContent.searchViewLocation

        val skyMall = LatLng(50.49456747309024, 30.560272889957194)
        map.addMarker(MarkerOptions().position(skyMall).title("Marker in SkyMall"))
        map.moveCamera(CameraUpdateFactory.newLatLng(skyMall))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(skyMall, 16f))
        map.uiSettings.apply {
            this.isMapToolbarEnabled = false
        }
        mapStylesManager.setMapStyle(map, MapStyles.STYLE_RETRO)

        mapSearchManager = MapSearchManager(this, map, searchView)
        binding.layoutNavigation.toolbarContent.searchViewLocation.setOnQueryTextListener(mapSearchManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val buttonMode = binding.layoutNavigation.toolbarContent.toolbar.menu.findItem(R.id.item_map_mode)
        val buttonStyle = binding.layoutNavigation.toolbarContent.toolbar.menu.findItem(R.id.item_map_style)

        when(item.itemId) {
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
                buttonStyle.isVisible = true
                mapStylesManager.getCurrentStyle(buttonStyle, buttonMode)
            }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
                buttonStyle.isVisible = false
            }
            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
                buttonStyle.isVisible = false
            }
            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
                buttonStyle.isVisible = false
            }
            R.id.item_map_style -> {
                mapStylesManager.setNighDayStyle(map, buttonStyle, buttonMode)
            }
        }
        return true
    }
}

/**
 * Некоторые устройства не будут поддерживать слишком близкое увеличение карты, есть функция для проверки getMinZoomLevel() в GoogleMap объекте.
 * Кратность Zoom:
 * 5 - Континент
 * 10 - город
 * 15 - улицы вокруг
 * 20 - здания вокруг
 */