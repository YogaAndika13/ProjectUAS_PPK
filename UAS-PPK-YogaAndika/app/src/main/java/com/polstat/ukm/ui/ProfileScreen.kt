package com.polstat.ukm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.polstat.ukm.R
import com.polstat.ukm.ui.theme.Purple80
import com.polstat.ukm.ui.theme.PurpleGrey80
import com.polstat.ukm.ui.theme.UkmTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    email: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
    showMessage: (Int, Int) -> Unit = { _, _ -> },
    showSpinner: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    if (profileViewModel.showConfirmDialog) {
        ConfirmDialog(
            onConfirmRequest = {
                profileViewModel.showConfirmDialog = false
                showSpinner()

                scope.launch {
                    when (profileViewModel.deleteAccount()) {
                        DeleteAccountResult.Success -> {
                            showMessage(R.string.sukses, R.string.berhasil_hapus_akun)
                            navController.navigate(UkmScreen.Login.name)
                        }
                        else -> showMessage(R.string.error, R.string.network_error)
                    }
                }
            },
            onDismissRequest = { profileViewModel.showConfirmDialog = false },
            message = R.string.konfirmasi_hapus_akun
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.padding(24.dp))
        Card (colors = CardDefaults.cardColors(containerColor = PurpleGrey80)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.menu_edit_profil),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Purple80
                )

                TextField(
                    value = profileViewModel.nameField,
                    singleLine = true,
                    onValueChange = { profileViewModel.updateNameField(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = PurpleGrey80 ),
                    label = {
                        Text(text = stringResource(id = R.string.nama))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )

                TextField(
                    value = email,
                    singleLine = true,
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(containerColor = PurpleGrey80 ),
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    enabled = false
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    onClick = {
                        showSpinner()

                        scope.launch {
                            when (profileViewModel.updateProfile()) {
                                UpdateProfileResult.Success -> showMessage(R.string.sukses, R.string.berhasil_ubah_profil)
                                UpdateProfileResult.Error -> showMessage(R.string.error, R.string.network_error)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple80)
                ) {
                    Text(text = stringResource(id = R.string.ubah_profil))
                }
            }
        }
        
        Spacer(modifier = Modifier.padding(12.dp))

        Card(colors = CardDefaults.cardColors(containerColor = PurpleGrey80)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 30.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.ubah_password),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Purple80
                )

                PasswordTextField(
                    value = profileViewModel.oldPasswordField,
                    onValueChange = { profileViewModel.updateOldPasswordField(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = PurpleGrey80),
//                    modifier = Modifier.background(PurpleGrey80),
                    label = {
                        Text(text = stringResource(id = R.string.password_lama))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    )
                )

                PasswordTextField(
                    value = profileViewModel.newPasswordField,
                    onValueChange = { profileViewModel.updateNewPasswordField(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = PurpleGrey80),
//                    modifier = Modifier.background(PurpleGrey80),
                    label = {
                        Text(text = stringResource(id = R.string.password_baru))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    )
                )

                PasswordTextField(
                    value = profileViewModel.confirmPasswordField,
                    onValueChange = { profileViewModel.updateConfirmPasswordField(it) },
                    colors = TextFieldDefaults.textFieldColors(containerColor = PurpleGrey80),
//                    modifier = Modifier.background(PurpleGrey80),
                    label = {
                        Text(text = stringResource(id = R.string.konfirmasi_password))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    )
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Button(onClick = {
                    showSpinner()

                    scope.launch {
                        when (profileViewModel.updatePassword()) {
                            UpdatePasswordResult.Success -> showMessage(R.string.sukses, R.string.berhasil_ubah_password)
                            UpdatePasswordResult.WrongPassword -> showMessage(R.string.error, R.string.password_salah)
                            UpdatePasswordResult.Mismatch -> showMessage(R.string.error, R.string.password_mismatch)
                            UpdatePasswordResult.Error -> showMessage(R.string.error, R.string.network_error)
                        }
                    }
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple80)) {
                    Text(text = stringResource(id = R.string.ubah_password))
                }
            }
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Button(
            onClick = { profileViewModel.showConfirmDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.hapus_akun))
        }

        Spacer(modifier = Modifier.padding(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    UkmTheme {
        ProfileScreen(
            email = "Testing@gmail.com",
            navController = rememberNavController()
        )
    }
}