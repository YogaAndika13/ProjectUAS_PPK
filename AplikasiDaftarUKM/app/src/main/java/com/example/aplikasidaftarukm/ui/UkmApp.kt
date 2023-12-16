package com.example.aplikasidaftarukm.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aplikasidaftarukm.R
import com.example.aplikasidaftarukm.data.UserState
import kotlinx.coroutines.launch

enum class UkmScreen {
    Login,
    Register,
    Home,
    Profile,
    UserManagement,
    EditUser
}

private const val TAG = "UkmApp"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UkmApp(
    navController: NavHostController = rememberNavController(),
    ukmAppViewModel: UkmAppViewModel = viewModel(factory = UkmAppViewModel.Factory)
) {
    val loggedInUser = ukmAppViewModel.userState.collectAsState().value
    val uiState = ukmAppViewModel.uiState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val showTopBar = when (navBackStackEntry?.destination?.route) {
        UkmScreen.Login.name, UkmScreen.Register.name -> false
        else -> true
    }
//    val showFloatingActionButton = when (navBackStackEntry?.destination?.route) {
//        UkmScreen.ProblemTypeManagement.name -> true
//        else -> false
//    }
//    val floatingActionButtonDestination = when (navBackStackEntry?.destination?.route) {
//        UkmScreen.ProblemTypeManagement.name -> UkmScreen.CreateProblemType.name
//        else -> ""
//    }

    if (uiState.value.showProgressDialog) {
        ProgressDialog(onDismissRequest = { ukmAppViewModel.dismissSpinner() })
    }
    if (uiState.value.showMessageDialog) {
        MessageDialog(
            onDismissRequest = { ukmAppViewModel.dismissMessageDialog() },
            onClose = { ukmAppViewModel.dismissMessageDialog() },
            title = uiState.value.messageTitle,
            message = uiState.value.messageBody
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                UkmDrawer(
                    user = loggedInUser,
                    navController = navController,
                    closeDrawer = {
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    },
                    logout = { ukmAppViewModel.logout() }
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                if (showTopBar) {
                    UkmAppBar(
                        onMenuClicked = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
//            floatingActionButton = {
//                if (showFloatingActionButton) {
//                    FloatingActionButton(onClick = {
//                        navController.navigate(floatingActionButtonDestination)
//                    }) {
//                        Text(
//                            text = stringResource(id = R.string.plus),
//                            fontWeight = FontWeight.ExtraBold,
//                            fontSize = 24.sp
//                        )
//                    }
//                }
//            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = if (loggedInUser.token == "") UkmScreen.Login.name else UkmScreen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = UkmScreen.Login.name) {
                    LoginScreen(
                        onLoginSuccess = {
                            // showProgressDialog = false
                            ukmAppViewModel.dismissSpinner()
                            navController.navigate(UkmScreen.Home.name)
                        },
                        onRegisterButtonClicked = { navController.navigate(UkmScreen.Register.name) },
                        showSpinner = { ukmAppViewModel.showSpinner() },
                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) }
                    )
                }

                composable(route = UkmScreen.Register.name) {
                    RegisterScreen(
                        onBackButtonClicked = { navController.navigate(UkmScreen.Login.name) },
                        showSpinner = { ukmAppViewModel.showSpinner() },
                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) }
                    )
                }

                composable(route = UkmScreen.Home.name) {
                    HomeScreen()
                }

                composable(route = UkmScreen.Profile.name) {
                    ProfileScreen(
                        email = loggedInUser.email,
                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) },
                        showSpinner = { ukmAppViewModel.showSpinner() },
                        navController = navController
                    )
                }

//                composable(route = UkmScreen.ProblemTypeManagement.name) {
//                    ProblemTypeManagementScreen(
//                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) },
//                        showSpinner = { ukmAppViewModel.showSpinner() }
//                    )
//                }

//                composable(route = UkmScreen.CreateProblemType.name) {
//                    CreateProblemTypeScreen(
//                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) },
//                        showSpinner = { ukmAppViewModel.showSpinner() },
//                        navController = navController
//                    )
//                }

                composable(route = UkmScreen.UserManagement.name) {
                    UserManagementScreen(
                        showMessage = { title, body -> ukmAppViewModel.showMessageDialog(title, body) },
                        showSpinner = { ukmAppViewModel.showSpinner() },
                        navController = navController
                    )
                }

                composable(
                    route = "${UkmScreen.EditUser.name}/{userId}",
                    arguments = listOf(navArgument("userId") {
                        type = NavType.LongType
                    })
                ) {
                    EditUserScreen(
                        editUserViewModel = viewModel(factory = EditUserViewModel.Factory)
                    )
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UkmAppBar(
    modifier: Modifier = Modifier,
    onMenuClicked: () -> Unit = {}
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bps),
                    contentDescription = stringResource(id = R.string.logo_bps),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = stringResource(id = R.string.app_name))
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(id = R.string.profile)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun UkmDrawer(
    user: UserState,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit = {},
    logout: suspend () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = closeDrawer) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.kembali)
                )
            }

            Text(
                text = stringResource(id = R.string.menu)
            )
        }

        DrawerNavigationItem(
            Icons.Filled.Home,
            text = R.string.menu_beranda
        ) {
            navController.navigate(UkmScreen.Home.name)
            closeDrawer()
        }

        if (user.isAdmin) {
//            DrawerNavigationItem(
//                icons = Icons.Filled.MailOutline,
//                text = R.string.menu_manajemen_jenis_masalah
//            ) {
//                navController.navigate(UkmScreen.ProblemTypeManagement.name)
//                closeDrawer()
//            }
            DrawerNavigationItem(
                Icons.Filled.Face,
                text = R.string.menu_manajemen_user
            ) {
                navController.navigate(UkmScreen.UserManagement.name)
                closeDrawer()
            }
        }

        DrawerNavigationItem(
            icons = Icons.Filled.Face,
            text = R.string.menu_edit_profil
        ) {
            navController.navigate(UkmScreen.Profile.name)
            closeDrawer()
        }
        DrawerNavigationItem(
            icons = Icons.Filled.ExitToApp,
            text = R.string.logout
        ) {
            scope.launch {
                logout()
                navController.navigate(UkmScreen.Login.name)
                closeDrawer()
            }
        }
    }
}

@Composable
fun DrawerNavigationItem(
    icons: ImageVector,
    @StringRes text: Int,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            icons,
            contentDescription = null
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = stringResource(id = text)
        )
    }
}