package com.golandco.golandcodriver.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.golandco.golandcodriver.R
import com.golandco.golandcodriver.databinding.ActivityMapsBinding
import com.golandco.golandcodriver.managers.mapsearch.AutocompleteSearchManager
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
    private lateinit var binding: ActivityMapsBinding

    private val mapStylesManager = MapStyleManager(this)
    private lateinit var autocompleteSearchManager: AutocompleteSearchManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autocompleteSearchManager = AutocompleteSearchManager(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        setSupportActionBar(binding.layoutNavigation.toolbarContent.toolbar)
        initDrawerMenu()
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

        autocompleteSearchManager.setupPlacesAutoComplete(map, supportFragmentManager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val buttonMode = binding.layoutNavigation.toolbarContent.toolbar.menu.findItem(R.id.item_map_mode)
        val buttonStyle = binding.layoutNavigation.toolbarContent.toolbar.menu.findItem(R.id.item_map_style)
        val toolbar = binding.layoutNavigation.toolbarContent.toolbar

        when(item.itemId) {
            R.id.normal_map -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
                buttonStyle.isVisible = true
                mapStylesManager.getCurrentStyle(buttonMode, toolbar)
            }
            R.id.hybrid_map -> {
                map.mapType = GoogleMap.MAP_TYPE_HYBRID
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
                toolbar.setNavigationIcon(R.drawable.ic_drawer_menu_button_night)
                buttonStyle.isVisible = false
            }
            R.id.satellite_map -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_night)
                toolbar.setNavigationIcon(R.drawable.ic_drawer_menu_button_night)
                buttonStyle.isVisible = false
            }
            R.id.terrain_map -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                buttonMode.icon = ContextCompat.getDrawable(this, R.drawable.ic_map_mode_day)
                toolbar.setNavigationIcon(R.drawable.ic_drawer_menu_button_day)
                buttonStyle.isVisible = false
            }
            R.id.item_map_style -> {
                mapStylesManager.setNighDayStyle(map, buttonStyle, buttonMode)
                mapStylesManager.getCurrentStyle(buttonMode, toolbar)
            }
        }
        return true
    }

    private fun initDrawerMenu() {
        val toolbar = binding.layoutNavigation.toolbarContent.toolbar

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.drawer_menu_open, R.string.drawer_menu_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toolbar.setNavigationIcon(R.drawable.ic_drawer_menu_button_day)
        toggle.syncState()

        toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}

/**
 * Некоторые устройства не будут поддерживать слишком близкое увеличение карты, есть функция для проверки getMinZoomLevel() в GoogleMap объекте.
 * Кратность Zoom:
 * 5 - Континент
 * 10 - город
 * 15 - улицы вокруг
 * 20 - здания вокруг
 *
 *
 * Show Buildings in 3D on the Map - необходимо для навигации по маршруту
 * Change ZOOM levels and Set Max/Min ZOOM level - необходим для блокировки изменения зума, а так же для перехода
 *
 */