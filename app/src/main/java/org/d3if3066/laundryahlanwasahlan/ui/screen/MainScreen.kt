package org.d3if3066.laundryahlanwasahlan.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3066.laundryahlanwasahlan.ui.theme.LaundryAhlanWaSahlanTheme
import org.d3if3066.laundryahlanwasahlan.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    //Untuk Backround
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    //Untuk Judul
                    titleContentColor = MaterialTheme.colorScheme.primary
                ))
        }
    ) {padding ->
        ScreenContent(Modifier.padding(padding))

    }
}
@Composable
fun ScreenContent(modifier: Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LaundryAhlanWaSahlanTheme {
        MainScreen()
    }
}