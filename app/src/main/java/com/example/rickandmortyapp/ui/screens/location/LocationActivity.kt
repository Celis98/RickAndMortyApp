package com.example.rickandmortyapp.ui.screens.location

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortyapp.data.viewmodel.LocationViewModel
import com.example.rickandmortyapp.databinding.ActivityLocationBinding
import kotlinx.coroutines.launch

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocationBinding
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUiStateLifecycle()
        getLocationId()
    }

    private fun getLocationId() {
        intent.extras?.getInt(LOCATION_ID)?.let { id ->
            locationViewModel.getLocationById(id)
        }
    }

    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            locationViewModel.uiState.collect { uiState ->
                with(binding) {
                    uiState.location?.let { location ->
                        tvLocationName.text = location.name
                        tvLocationType.text = location.type
                        tvLocationDimension.text = location.dimension
                    }
                }
            }
        }
    }

    companion object {
        const val LOCATION_ID = "LOCATION_ID"
    }
}