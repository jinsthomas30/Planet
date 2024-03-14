package com.example.planet.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planet.R
import com.example.planet.NavigationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Splash(navController: NavHostController,modifier: Modifier) {
    SplashScreenView(modifier)
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(NavigationItem.PLANT_LIST.route){
            popUpTo(NavigationItem.PLANT_LIST.route) {
                inclusive = true
            }
        }
    }

}
@Composable
fun SplashScreenView(modifier: Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.planet_icon),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
        )
        Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 30.sp
        )
    }
}
