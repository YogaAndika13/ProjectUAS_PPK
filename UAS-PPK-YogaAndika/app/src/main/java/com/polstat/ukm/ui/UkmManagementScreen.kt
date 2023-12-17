package com.polstat.ukm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polstat.ukm.R
import kotlinx.coroutines.launch

@Composable
fun UkmManagementScreen(
    modifier: Modifier = Modifier,
    ukmViewModel: UkmManagementViewModel = viewModel(factory = UkmManagementViewModel.Factory),
    showMessage: (Int, Int) -> Unit = { _, _ -> },
    showSpinner: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    if (showConfirmDialog) {
        ConfirmDialog(
            onConfirmRequest = {
                showConfirmDialog = false
                showSpinner()
                scope.launch {
                    when (ukmViewModel.deleteUkm()) {
                        DeleteUkmResult.Success -> {
                            showMessage(R.string.sukses, R.string.berhasil_hapus_jenis_masalah)
                            ukmViewModel.getUkms()
                        }
                        DeleteUkmResult.Error -> showMessage(R.string.error, R.string.network_error)
                    }
                }
            },
            onDismissRequest = { showConfirmDialog = false },
            message = R.string.hapus_kegiatan_ukm
        )
    }

    UkmManagementScreenContent(
        ukmUiState = ukmViewModel.ukmUiState,
        onDeleteClicked = { id ->
            ukmViewModel.selectedId = id
            showConfirmDialog = true
        }
    )
}

@Composable
private fun UkmManagementScreenContent(
    ukmUiState: UkmUiState,
    modifier: Modifier = Modifier,
    onDeleteClicked: (Long) -> Unit = {}
) {
    when(ukmUiState) {
        is UkmUiState.Error -> {
            Text(text = "Error")
        }
        is UkmUiState.Loading -> {
            Text(text = "Loading")
        }
        is UkmUiState.Success -> {
            val ukms = ukmUiState.ukms
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(items = ukms) { ukm ->
                    ItemCard(
                        title = ukm.namaUkm,
                        description = ukm.deskripsiUkm,
                        options = {
                            Column {
                                DrawerNavigationItem(
                                    icons = Icons.Filled.Delete,
                                    text = R.string.hapus_kegiatan_ukm,
                                    onClick = { ukm.id?.let { onDeleteClicked(it) } }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}