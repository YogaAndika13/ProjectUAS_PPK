package com.example.aplikasidaftarukm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.aplikasidaftarukm.ui.UkmApp
import com.example.aplikasidaftarukm.ui.theme.AplikasiDaftarUKMTheme

//import com.polstat.singadu.ui.SingaduApp
//import com.polstat.singadu.ui.theme.SingaduTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiDaftarUKMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UkmApp()
                }
            }
        }
    }
}
