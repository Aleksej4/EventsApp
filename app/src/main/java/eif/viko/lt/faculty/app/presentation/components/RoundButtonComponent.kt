package eif.viko.lt.faculty.app.presentation.components

import android.graphics.drawable.Icon
import android.util.Size
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary

@Composable
fun RoundButtonComponent(
    icon: ImageVector,
    onClick: () -> Unit,
    size: Dp = 70.dp
){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .size(size),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkerGreen
        )
    ){
        Icon(
            imageVector = icon,
            contentDescription = "BrowseEvent",
            tint = Primary,
        )
    }
}