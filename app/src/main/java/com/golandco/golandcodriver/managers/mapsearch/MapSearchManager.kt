package com.golandco.golandcodriver.managers.mapsearch

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.SearchView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.lang.IndexOutOfBoundsException

class MapSearchManager(private val context: Context, private val map: GoogleMap, private val searchView: SearchView): SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        val location = searchView.query.toString()
        val addressList: List<Address>

        if (location != "") {
            val geocoder = Geocoder(context)

            try {
                addressList = geocoder.getFromLocationName(location, 1)
                val address = addressList[0]
                val point = LatLng(address.latitude, address.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
            } catch (e: IndexOutOfBoundsException) {
                Log.d("AppError", "AddressList: ${e.message}")
            } catch (e: IOException) {
                Log.d("AppError", "AddressList: ${e.message}")
            }
        }

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


}