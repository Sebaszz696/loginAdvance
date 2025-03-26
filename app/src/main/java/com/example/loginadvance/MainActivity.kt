/**
 * Aplicación de Login con Jetpack Compose
 * 
 * Credenciales de acceso:
 * Usuario: Admin
 * Contraseña: Admin
 * 
 * La aplicación implementa un sistema de navegación entre tres pantallas:
 * 1. Login: Pantalla inicial para autenticación
 * 2. Success: Pantalla de bienvenida con opciones (no funcionales)
 * 3. Denied: Pantalla de error con opciones de recuperación
 */

package com.example.loginadvance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginadvance.ui.theme.LoginAdvanceTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Actividad principal que configura la navegación y el tema de la aplicación
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginAdvanceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Configuración del controlador de navegación
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController) }
                        composable("success/{username}") { backStackEntry ->
                            SuccessScreen(
                                username = backStackEntry.arguments?.getString("username") ?: "",
                                navController = navController
                            )
                        }
                        composable("denied") { DeniedScreen(navController) }
                    }
                }
            }
        }
    }
}

/**
 * Pantalla de inicio de sesión
 * 
 * @param navController Controlador de navegación para cambiar entre pantallas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(navController: androidx.navigation.NavController) {
    // Estados para los campos de texto
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Campo de usuario
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Campo de contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Botón de inicio de sesión
        Button(
            onClick = {
                // Validación de credenciales
                if (username == "Admin" && password == "Admin") {
                    navController.navigate("success/$username")
                } else {
                    navController.navigate("denied")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}

/**
 * Pantalla de éxito que se muestra después de un inicio de sesión exitoso
 * 
 * @param username Nombre del usuario que inició sesión
 * @param navController Controlador de navegación para cambiar entre pantallas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessScreen(username: String, navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mensaje de bienvenida
        Text(
            text = "¡Bienvenido $username!",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // Lista de botones no funcionales
        listOf("Ver Perfil", "Configuración", "Notificaciones").forEach { text ->
            Button(
                onClick = { /* No implementado */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Botón de cerrar sesión
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar Sesión")
        }
    }
}

/**
 * Pantalla de acceso denegado que se muestra cuando las credenciales son incorrectas
 * 
 * @param navController Controlador de navegación para cambiar entre pantallas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeniedScreen(navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mensaje de error
        Text(
            text = "Acceso Denegado",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        
        Text(
            text = "Usuario o contraseña incorrectos",
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Lista de botones no funcionales
        listOf("Registrarse", "Olvidé mi contraseña").forEach { text ->
            Button(
                onClick = { /* No implementado */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text)
            }
        }
        
        // Botón para regresar al login
        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}

/**
 * Preview de la pantalla de login
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginAdvanceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(rememberNavController())
        }
    }
}

/**
 * Preview de la pantalla de éxito
 */
@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    LoginAdvanceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SuccessScreen("Admin", rememberNavController())
        }
    }
}

/**
 * Preview de la pantalla de acceso denegado
 */
@Preview(showBackground = true)
@Composable
fun DeniedScreenPreview() {
    LoginAdvanceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DeniedScreen(rememberNavController())
        }
    }
}

/**
 * Preview de toda la aplicación
 */
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LoginAdvanceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("success/{username}") { backStackEntry ->
                    SuccessScreen(
                        username = backStackEntry.arguments?.getString("username") ?: "",
                        navController = navController
                    )
                }
                composable("denied") { DeniedScreen(navController) }
            }
        }
    }
}