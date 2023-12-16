package com.example.aplikasidaftarukm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aplikasidaftarukm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    editUserViewModel: EditUserViewModel,
    modifier: Modifier = Modifier
) {
    val userUiState = editUserViewModel.userUiState

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Column {

            TextField(
                value = userUiState.name,
                onValueChange = {},
                enabled = false,
                label = {
                    Text(text = stringResource(id = R.string.nama))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(5.dp))

            TextField(
                value = userUiState.email,
                onValueChange = {},
                enabled = false,
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                modifier = Modifier.fillMaxWidth()
            )

            EditUserRoleCheckbox(
                checked = editUserViewModel.userHasRole("ROLE_ADMIN"),
                onCheckedChange = {},
                text = stringResource(id = R.string.role_admin)
            )

        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.hapus_akun))
        }
    }
}

@Composable
fun EditUserRoleCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)

        Text(text = text)
    }
}