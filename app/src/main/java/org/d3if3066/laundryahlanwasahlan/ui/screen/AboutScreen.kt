package org.d3if3066.laundryahlanwasahlan.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3066.laundryahlanwasahlan.R
import org.d3if3066.laundryahlanwasahlan.ui.theme.LaundryAhlanWaSahlanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary)
                    }
                },
                title = { Text(text = stringResource(id = R.string.tentang_aplikasi)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    //Untuk Judul
                    titleContentColor = MaterialTheme.colorScheme.primary
                ))
        }
    ) {padding ->
        ContentAbout(Modifier.padding(padding))

    }
}

@Composable
fun ContentAbout(modifier: Modifier) {

    val imageModifier = Modifier.fillMaxWidth()

    Image(
        painter = painterResource(R.drawable.banner),
        contentDescription = stringResource(R.string.imgae_description),
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview() {
    LaundryAhlanWaSahlanTheme {
        AboutScreen(rememberNavController())
    }
}