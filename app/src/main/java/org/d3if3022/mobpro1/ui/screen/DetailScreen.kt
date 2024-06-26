package org.d3if3022.mobpro1.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.d3if3022.mobpro1.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if3022.mobpro1.theme.Mobpro1Theme
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3022.mobpro1.database.BarangDb
import org.d3if3022.mobpro1.util.ViewModelFactory


const val KEY_ID_BARANG = "idBarang"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val contenx = LocalContext.current
    val db = BarangDb.getInstance(contenx)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var ukuran by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf( "") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBarang(id) ?: return@LaunchedEffect
        nama = data.nama
        jenis = data.jenis
        jumlah = data.jumlah
        harga = data.harga
        ukuran = data.ukuran
        deskripsi = data.deskripsi

    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_mahasiswa))
                    else
                        Text(text = stringResource(id = R.string.edit_mahasiswa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama == "" || jenis == "" || jumlah == "" || harga == "" || ukuran == ""|| deskripsi == "") {
                            Toast.makeText(contenx, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama, jenis, jumlah, harga, ukuran, deskripsi)
                        } else {
                            viewModel.update(id, nama, jenis, jumlah, harga, ukuran, deskripsi)
                        }
                        navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if ( id != null ) {
                        DeleteAction { showDialog = true}
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormBarang(
            name = nama,
            onNameChange = { nama = it },
            jenis = jenis,
            onJenisChange = { jenis = it },
            jumlah = jumlah,
            onJumlahChange = { jumlah = it },
            harga = harga,
            onHargaChange = { harga = it },
            ukuran = ukuran,
            onUkuranChange = { ukuran = it },
            deskripsi = deskripsi,
            onDeskripsiChange = { deskripsi = it },
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var  expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormBarang(
    name: String, onNameChange: (String) -> Unit,
    jenis: String, onJenisChange: (String) -> Unit,
    jumlah: String, onJumlahChange: (String) -> Unit,
    harga: String, onHargaChange: (String) -> Unit,
    ukuran: String, onUkuranChange: (String) -> Unit,
    deskripsi: String, onDeskripsiChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedCard(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ){
            Column {
                listOf(
                    "Kaos",
                    "Celana",
                    "Jaket",
                    "Kemeja",
                    "Jas"
                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onJenisChange(classOption) }
                    ) {
                        RadioButton(
                            selected = classOption == jenis,
                            onClick = { onJenisChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        OutlinedTextField(
            value = jumlah,
            onValueChange = { onJumlahChange(it) },
            label = { Text(text = stringResource(R.string.jumlah)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = harga,
            onValueChange = { onHargaChange(it) },
            label = { Text(text = stringResource(R.string.harga)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedCard(modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ){
            Column {
                listOf(
                    "Ukuran S",
                    "Ukuran M",
                    "Ukuran L",
                    "Ukuran XL",
                    "Ukuran XXL"
                ).forEach { classOption ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onUkuranChange(classOption) }
                    ) {
                        RadioButton(
                            selected = classOption == ukuran,
                            onClick = { onUkuranChange(classOption) }
                        )
                        Text(text = classOption, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
        OutlinedTextField(
            value = deskripsi,
            onValueChange = { onDeskripsiChange(it) },
            label = { Text(text = stringResource(R.string.deskripsi)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Mobpro1Theme {
        DetailScreen(rememberNavController())
    }
}