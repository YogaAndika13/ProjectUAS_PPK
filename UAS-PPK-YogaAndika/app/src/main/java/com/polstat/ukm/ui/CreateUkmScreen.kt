package com.polstat.ukm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.polstat.ukm.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUkmScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    createUkmViewModel: CreateUkmViewModel = viewModel(factory = CreateUkmViewModel.Factory),
    showMessage: (Int, Int) -> Unit = { _, _ -> },
    showSpinner: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = createUkmViewModel.nameField,
            onValueChange = { createUkmViewModel.updateNameField(it) },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.nama_kegiatan_ukm))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        TextField(
            value = createUkmViewModel.tempatField,
            onValueChange = { createUkmViewModel.updateTempatField(it)},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.tempat_kegiatan_ukm))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        TextField(
            value = createUkmViewModel.deskripsiField,
            onValueChange = { createUkmViewModel.updateDeskripsifield(it)},
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.deskripsi_kegiatan_ukm))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )

        Button(
            onClick = {
                showSpinner()
                scope.launch {
                    when (createUkmViewModel.createUkm()) {
                        CreateUkmResult.Success -> {
                            showMessage(R.string.sukses, R.string.berhasil_nambah_kegiatan_ukm)
                            navController.navigate(UkmScreen.UkmManagement.name)
                        }
                        CreateUkmResult.Error -> showMessage(R.string.error, R.string.network_error)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.tambah))
        }
    }
}