package org.d3if3066.laundryahlanwasahlan.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import org.d3if3066.laundryahlanwasahlan.R
import org.d3if3066.laundryahlanwasahlan.ui.theme.LaundryAhlanWaSahlanTheme

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
    var namaPelanggan by remember{ mutableStateOf("") }
    var beratLaundry by remember { mutableStateOf("") }

    var namaPelangganError by rememberSaveable { mutableStateOf(false) }
    var beratLaundryError by rememberSaveable { mutableStateOf(false) }

    val radioOptions = listOf(
        stringResource(R.string.cuci_kering),
        stringResource(R.string.cuci_setrika)
    )

    var kategori by rememberSaveable { mutableStateOf(radioOptions[0]) }
    var totalHarga by rememberSaveable { mutableFloatStateOf(0f) }

    val context = LocalContext.current

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = namaPelanggan,
            onValueChange = {namaPelanggan = it},
            label = { Text(text = stringResource(R.string.nama_pelanggan))},
            isError = namaPelangganError,
            supportingText = {ErrorHint(namaPelangganError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = beratLaundry,
            onValueChange = {beratLaundry = it},
            label = { Text(text = stringResource(R.string.berat_laundry))},
            isError = beratLaundryError,
            trailingIcon = {IconPicker(beratLaundryError, "kg")},
            supportingText = {ErrorHint(beratLaundryError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach{text ->
                LaundryOption(
                    label = text,
                    isSelected = kategori == text,
                    modifier = Modifier
                        .selectable(
                            selected = kategori == text,
                            onClick = { kategori = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }

        Button(
            onClick = {
                namaPelangganError = (namaPelanggan == "" || namaPelanggan.isDigitsOnly())
                beratLaundryError = (beratLaundry == "" || beratLaundry == "0")
                
                if(namaPelangganError || beratLaundryError) {
                    return@Button
                }

                totalHarga = hargaLaundry(beratLaundry.toFloat(), kategori == radioOptions[0])
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if(totalHarga != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(R.string.harga, totalHarga),
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template, namaPelanggan, kategori, beratLaundry, totalHarga)
                    )
                },
                modifier = Modifier.padding(top = 60.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan) )
            }
        }

    }
}

@Composable
fun LaundryOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null )
    }
    else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }

}
private fun hargaLaundry(beratLaundry: Float, isDry: Boolean): Float {
    return if (isDry) {
        beratLaundry * 5000
    }
    else {
        beratLaundry * 6000
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if(shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LaundryAhlanWaSahlanTheme {
        MainScreen()
    }
}