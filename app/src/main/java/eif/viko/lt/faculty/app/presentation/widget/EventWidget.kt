package eif.viko.lt.faculty.app.presentation.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import eif.viko.lt.faculty.app.data.Event
import eif.viko.lt.faculty.app.data.EventRepository
import eif.viko.lt.faculty.app.data.EventRepositoryImpl
import eif.viko.lt.faculty.app.presentation.components.EventComponent
import eif.viko.lt.faculty.app.presentation.events.HomeEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventWidget: GlanceAppWidget(){

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        
        val repository = EventRepositoryImpl(
            firebaseDatabase = FirebaseDatabase.getInstance(),
            firebaseStorage = FirebaseStorage.getInstance()
        )
        val viewModel = EventWidgetViewModel(repository)
        
        provideContent {
            EventWidgetContent(viewModel = viewModel)
        }
    }
}

@Composable
fun EventWidgetContent(
    viewModel: EventWidgetViewModel
){
    val events = viewModel.events.collectAsState(initial = emptyList())
    Text(text = events.value[0].title)
}