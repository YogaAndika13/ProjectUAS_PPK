package com.polstat.ukm.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.polstat.ukm.UkmApplication
import com.polstat.ukm.data.UkmRepository
import com.polstat.ukm.data.UserPreferencesRepository
import com.polstat.ukm.model.Ukm
import kotlinx.coroutines.launch

private const val TAG = "CreateUkmViewModel"

class CreateUkmViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val ukmRepository: UkmRepository
) : ViewModel() {
    private lateinit var token: String
    var nameField by mutableStateOf("")
        private set
    var tempatField by mutableStateOf("")
        private set
    var deskripsiField by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect { user ->
                token = user.token
            }
        }
    }

    suspend fun createUkm(): CreateUkmResult {
        try {
            ukmRepository.createUkm(token, Ukm(
                id = null,
                namaUkm = nameField,
                deskripsiUkm = deskripsiField
            ))
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            return CreateUkmResult.Error
        }

        return CreateUkmResult.Success
    }

    fun updateNameField(newName: String) {
        nameField = newName
    }

    fun updateTempatField(newTempat: String) {
        tempatField = newTempat
    }

    fun updateDeskripsifield(newDeskripsi: String) {
        deskripsiField = newDeskripsi
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UkmApplication)
                val ukmRepository = application.container.ukmRepository
                CreateUkmViewModel(
                    userPreferencesRepository = application.userPreferenceRepository,
                    ukmRepository = ukmRepository
                )
            }
        }
    }
}

enum class CreateUkmResult {
    Success,
    Error
}