package eif.viko.lt.faculty.app.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImagePickerComponent(
    onImagePicked: (Uri) -> Unit
){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){uri: Uri? ->
        uri?.let {
            onImagePicked(it)
        }
    }

    RoundButtonComponent(
        icon = Icons.Default.Add,
        onClick = { launcher.launch("image/*")}
    )
}