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
                ) {MainView()}
            }
        }
    }
}

// Composable con la vista principal:
@Composable
fun MainView() {
    //Variables by remember:
    var showDialog by remember {mutableStateOf(false)} //Varaible para controlar cuando se muestra el Dialog
    var name by remember {mutableStateOf("")} // Variable para mostrar el nombre insertado
    var acceptCounter by remember {mutableStateOf(0)} // Contador de veces que se ha pulsado Aceptar
    var cancelCounter by remember {mutableStateOf(0)} // Contador de veces que se ha pulsado Cancelar

    // Botón principal:
    Row {
        // Caja botón:
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
            contentAlignment = Alignment.Center){
            Button(onClick = {showDialog = true}, modifier = Modifier.height(50.dp).width(150.dp)){Text("Saludar")}
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Frase para saludar, si no hay nombre, no se muestra el texto:
    Row {
        // Caja textview:
        Box(modifier = Modifier.fillMaxSize().padding(top = 100.dp),
            contentAlignment = Alignment.Center) {
            Text(text = if (name.isNotEmpty()) "Hola, $name" else "")
        }
    }

    // Ventana Dialog con las posibilidades que da el programa (Aceptar, Limpiar y Cancelar):
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

    // Contador de botones clickados, si se pulsa aceptar, sumará 1 a la variable accepCounter
    Box(modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
        contentAlignment = Alignment.Center
    ) {Text(text = "A$acceptCounter C$cancelCounter")}
}

// Compasable con el Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryView(
    //Variables:
    name: String,
    onAccept: (String) -> Unit,
    onCancel: () -> Unit,
    onClear: () -> Unit
) {
    var newName by remember {mutableStateOf(name)} //Variable para visualizar el nombre:

    //Ventana Dialog:
    Dialog(onDismissRequest = {
        onClear()
        onCancel()
        onClear() }
    ){
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(15.dp)
        ) {// Contenido del diálogo:
            Column(modifier = Modifier.fillMaxWidth().padding(15.dp),
            ) {//Titulo:
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    // Texto Configuración:
                    Text(
                        text = "Configuración",
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black,
                        fontSize = 20.sp
                    )}

                Spacer(modifier = Modifier.height(15.dp))

                // Frase para introducir el nombre arriba del TextField y centrado:
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    // Texto aqui:
                    Text(text = "Introduce tu nombre")}

                Spacer(modifier = Modifier.height(8.dp))

                // TextField para editar el nombre:
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

//Preview de la pantalla principal:
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppTheme {MainView()}
}
