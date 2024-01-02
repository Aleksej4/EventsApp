package eif.viko.lt.faculty.app.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen

@Composable
fun OutlinedButtonComponent (
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .size(width = 130.dp, height = 50.dp),
        border = BorderStroke(2.dp, DarkerGreen),

    ) {
        Text(
            text = buttonText,
            color = DarkerGreen,
            fontFamily = FontFamily.Serif
        )
    }
}