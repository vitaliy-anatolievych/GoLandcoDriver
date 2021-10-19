package com.golandco.golandcodriver.managers.mapstyle

import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions
import java.lang.Exception

class MapStyleManager(private val context: Context) {

    fun setMapStyle(googleMap: GoogleMap, mapStyle: Int) {
        try {
            val success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, mapStyle))
            if (!success) {
                Log.d("AppError", "Ошибка загрузки стиля")
            }
        } catch (e: Exception) {
            Log.d("AppError", e.toString())
        }
    }
}