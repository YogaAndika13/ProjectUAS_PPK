package com.example.aplikasidaftarukm.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.aplikasidaftarukm.UkmApplication
import com.example.aplikasidaftarukm.data.UserPreferencesRepository
import com.example.aplikasidaftarukm.data.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class UkmAppViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(UkmAppUiState())
    val uiState: StateFlow<UkmAppUiState> = _uiState.asStateFlow()

    val userState: StateFlow<UserState> = userPreferencesRepository.user.map { user ->
        user
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserState(
            "",
            "",
            "",
            isAdmin = false
        )
    )

    fun showSpinner() {
        _uiState.update { currentState ->
            currentState.copy(
                showProgressDialog = true
            )
        }
    }

    fun dismissSpinner() {
        _uiState.update { currentState ->
            currentState.copy(
                showProgressDialog = false
            )
        }
    }

    fun showMessageDialog(@StringRes title: Int, @StringRes body: Int) {
        _uiState.update {  currentState ->
            currentState.copy(
                showProgressDialog = false,
                showMessageDialog = true,
                messageTitle = title,
                messageBody = body
            )
        }
    }

    fun dismissMessageDialog() {
        _uiState.update {  currentState ->
            currentState.copy(
                showMessageDialog = false
            )
        }
    }

    suspend fun logout() {
        userPreferencesRepository.saveToken("")
        userPreferencesRepository.saveName("")
        userPreferencesRepository.saveEmail("")
        userPreferencesRepository.saveIsAdmin(false)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UkmApplication)
                UkmAppViewModel(
                    userPreferencesRepository = application.userPreferenceRepository
                )
            }
        }
    }
}

data class UkmAppUiState(
    val showProgressDialog: Boolean = false,
    val showMessageDialog: Boolean = false,
    @StringRes val messageTitle: Int = 0,
    @StringRes val messageBody: Int = 0
)