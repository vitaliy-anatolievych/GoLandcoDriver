package com.golandco.golandcodriver.managers.mapstyle

import android.content.Context
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.golandco.golandcodriver.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import java.lang.Exception

class MapStyleManager(private val context: Context) {

    private var currentMapStyle: Int = 0

    fun setMapStyle(googleMap: GoogleMap, mapStyle: Int) {
        try {
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, mapStyle))
            currentMapStyle = mapStyle
            if (!success) {
                Log.d("AppError", "Ошибка загрузки стиля")
            }
        } catch (e: Exception) {
            Log.d("AppError", e.toString())
        }
    }

    // плохой метод, требует постоянного вмешательства, позже придумать лучшее решение
    fun setNighDayStyle(map: GoogleMap, buttonStyle: MenuItem, buttonMode: MenuItem) {
        if (this.currentMapStyle == MapStyles.STYLE_RETRO) {
            this.setMapStyle(map, MapStyles.STYLE_NIGHT)
            buttonStyle.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_style_night)
            buttonMode.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_mode_night)
        } else if (this.currentMapStyle == MapStyles.STYLE_NIGHT) {
            this.setMapStyle(map, MapStyles.STYLE_RETRO)
            buttonStyle.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_style_day)
            buttonMode.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_mode_day)
        }
    }

    // плохой метод, требует постоянного вмешательства, позже придумать лучшее решение
    fun getCurrentStyle(buttonMode: MenuItem?, toolbar: androidx.appcompat.widget.Toolbar?) {
        if (this.currentMapStyle == MapStyles.STYLE_RETRO) {
            buttonMode?.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_mode_day)
            toolbar?.setNavigationIcon(R.drawable.ic_drawer_menu_button_day)
        } else if (this.currentMapStyle == MapStyles.STYLE_NIGHT) {
            buttonMode?.icon = ContextCompat.getDrawable(context, R.drawable.ic_map_mode_night)
            toolbar?.setNavigationIcon(R.drawable.ic_drawer_menu_button_night)
        }
    }
}