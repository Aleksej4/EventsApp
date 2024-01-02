package eif.viko.lt.faculty.app.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputComponent (
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation
) {
    var isError by rememberSaveable { mutableStateOf(false) }
    var isTyping by remember { mutableStateOf(false) }

    LaunchedEffect(value){
        if (isTyping){
            isError = value.isEmpty()
        }
        isTyping = false
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isTyping = true
        },
        modifier = modifier
            .padding(12.dp)
            .width(300.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = DarkerGreen,
            focusedBorderColor = Secondary
        ),
        isError = isError,
        supportingText = {
            if (isError){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Fill in the data",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        placeholder = {
            Text(
                text = hintText,
                color = Color.Gray,
                fontFamily = FontFamily.Serif
            )
        },
        textStyle = TextStyle(
            fontFamily = FontFamily.Serif
        )
    )
}