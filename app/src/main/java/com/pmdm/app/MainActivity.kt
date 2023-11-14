package com.pmdm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.pmdm.app.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    var showDialog by remember {mutableStateOf(false)}
    var name by remember {mutableStateOf("")}
    var acceptCounter by remember {mutableStateOf(0)}
    var cancelCounter by remember {mutableStateOf(0)}

    Row {
        // Caja botón:
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            contentAlignment = Alignment.Center){
            Button(onClick = {showDialog = true}, modifier = Modifier
                .height(50.dp)
                .width(150.dp)) {
                Text("Saludar")}
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row {
        // Caja textview:
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
            contentAlignment = Alignment.Center) {
            Text(text = if (name.isNotEmpty()) "Hola, $name" else "")
        }
    }

    // Ventana de diálogo
    if (showDialog) {
        SecondaryView(
            name = name,
            onAccept = {
                acceptCounter++
                name = it
                showDialog = false
            },
            onCancel = {
                cancelCounter++
                showDialog = false
            },
            onClear = {name = ""}
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Contador de botones
    Box(modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {Text(text = "A$acceptCounter C$cancelCounter")}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryView(
    name: String,
    onAccept: (String) -> Unit,
    onCancel: () -> Unit,
    onClear: () -> Unit
) {
    var newName by remember { mutableStateOf(name) }

    Dialog(onDismissRequest = {
        onClear()
        onCancel()
        onClear() }
    ){
        Box(
            modifier = Modifier
                .background(Color.White) // Establece el fondo blanco
                .padding(15.dp) // Ajusta el relleno según sea necesario
        ) {
            // Contenido del diálogo aquí
            Column(modifier = Modifier.fillMaxWidth().padding(15.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    // Título Configuración
                    Text(
                        text = "Configuración",
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black, // Ajusta el color del texto según sea necesario
                        fontSize = 20.sp // Ajusta el tamaño de la fuente según sea necesario
                    )}


                Spacer(modifier = Modifier.height(15.dp))

                // Etiqueta "Introduce tu nombre"
                Text(text = "Introduce tu nombre")

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para editar el nombre:
                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = { Text("Nombre") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Botones Aceptar, Limpiar y Cancelar
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botón "Aceptar"
                    TextButton(onClick = {onAccept(newName)}) {Text("Aceptar")}

                    // Botón "Limpiar"
                    TextButton(onClick = {newName = ""}) {Text("Limpiar")}

                    // Botón "Cancelar"
                    TextButton(onClick = {onCancel()}) {Text("Cancelar")}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {MainView()}
}
