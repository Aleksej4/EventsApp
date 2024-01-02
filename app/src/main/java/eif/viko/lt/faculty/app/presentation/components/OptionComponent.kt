package eif.viko.lt.faculty.app.presentation.components

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary

@Composable
fun OptionComponent (
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
){
    Box (
        modifier = Modifier
            .size(width = 350.dp, height = 50.dp)
            .clickable { onClick() }
            .padding(2.dp)
            .shadow(2.dp)
            .border(2.dp, DarkerGreen)
    ){
        Box (
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = DarkerGreen
            )
        }
        Box (
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = title,
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp
            )
        }
    }
}