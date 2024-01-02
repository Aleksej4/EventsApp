package eif.viko.lt.faculty.app.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary

@Composable
fun FilledButtonComponent (
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(width = 130.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkerGreen,
            contentColor = Primary
        )
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily.Serif
        )
    }
}