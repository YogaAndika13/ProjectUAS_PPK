package com.example.aplikasidaftarukm.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aplikasidaftarukm.UkmApplication
import com.example.aplikasidaftarukm.data.UserPreferencesRepository
import com.example.aplikasidaftarukm.data.UserRepository
import com.example.aplikasidaftarukm.model.Role
import com.example.aplikasidaftarukm.model.User
//import com.polstat.singadu.SingaduApplication
//import com.polstat.singadu.data.UserPreferencesRepository
//import com.polstat.singadu.data.UserRepository
//import com.polstat.singadu.model.Role
//import com.polstat.singadu.model.User
import kotlinx.coroutines.launch

private const val TAG = "EditUserViewModel"

class EditUserViewModel(
    savedStateHandle: SavedStateHandle,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var userUiState by mutableStateOf(User(id = null, name = "", email = "", password = "", roles = null))
        private set

    private lateinit var token: String
    private val itemId: Long = checkNotNull(savedStateHandle["userId"])

    init {
        viewModelScope.launch {
            userPreferencesRepository.user.collect { user ->
                token = user.token
                try {
                    userUiState = userRepository.getUserById(token, itemId)
                } catch (e: Exception) {
                    Log.e(TAG, "Exception: ${e.message}")
                    Log.e(TAG, e.stackTraceToString())
                }
            }

        }
    }

    fun userHasRole(roleName: String): Boolean {
        val roles: List<Role> = userUiState.roles ?: listOf()
        return roles.any { role -> role.name == roleName }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UkmApplication)
                EditUserViewModel(
                    this.createSavedStateHandle(),
                    userPreferencesRepository = application.userPreferenceRepository,
                    userRepository = application.container.userRepository
                )
            }
        }
    }

}