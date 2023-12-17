package com.polstat.ukm.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import java.io.IOException

private const val TAG = "UkmManagementViewModel"

class UkmManagementViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val ukmRepository: UkmRepository
) : ViewModel() {

    private lateinit var token: String

    var ukmUiState: UkmUiState by mutableStateOf(UkmUiState.Loading)
        private set

    var selectedId: Long by mutableLongStateOf(0)

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect { user ->
                token = user.token
            }
        }
        getUkms()
    }

    fun getUkms() {
        viewModelScope.launch {
            ukmUiState = UkmUiState.Loading
            ukmUiState = try {
                UkmUiState.Success(ukmRepository.getUkms(token))
            } catch(e: IOException) {
                Log.e(TAG, "IOException: ${e.message}")
                UkmUiState.Error
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
                UkmUiState.Error
            }
        }
    }

    suspend fun deleteUkm(): DeleteUkmResult {
        try {
            ukmRepository.deleteUkm(token, selectedId)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}")
            return DeleteUkmResult.Error
        }

        return DeleteUkmResult.Success
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UkmApplication)
                val ukmRepository = application.container.ukmRepository
                UkmManagementViewModel(
                    application.userPreferenceRepository,
                    ukmRepository
                )
            }
        }
    }

}

sealed interface UkmUiState {
    data class Success(val ukms: List<Ukm>): UkmUiState
    object Error: UkmUiState
    object Loading: UkmUiState
}

enum class DeleteUkmResult {
    Success,
    Error
}