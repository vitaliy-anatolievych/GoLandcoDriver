package com.golandco.golandcodriver.managers.mapsearch

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.golandco.golandcodriver.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

/**
 *           Для изменени иконки лупы под ночную тему попробовать
 *           ImageView searchIcon = (ImageView)
 *           ((LinearLayout)autocompleteFragment.getView()).getChildAt(0);
 *           searchIcon.setImageDrawable(getResources().getDrawable(R.drawable.search));
 */
class AutocompleteSearchManager(private val context: Context) {

    private var placesClient: PlacesClient
    private var placeFields = listOf(
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.LAT_LNG
    )
    init {
        Places.initialize(context, context.resources.getString(R.string.google_maps_key))
        placesClient = Places.createClient(context)
    }

    fun setupPlacesAutoComplete(map: GoogleMap, supportFragmentManager: FragmentManager) {
        val autoCompleteFragment =  supportFragmentManager
            .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        autoCompleteFragment.setPlaceFields(placeFields)
        autoCompleteFragment.setHint(context.getText(R.string.search_hint))

        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                Toast.makeText(context, p0.statusMessage, Toast.LENGTH_LONG).show()
                Log.d("AppError", "PlaceSelectionError: ${p0.statusMessage}")
            }

            override fun onPlaceSelected(p0: Place) {
                map.clear()
                if (p0.latLng != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(p0.latLng!!, 18f))
                    map.addMarker(MarkerOptions().position(p0.latLng!!).title(p0.name))
                }
            }
        })
    }
}