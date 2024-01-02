package eif.viko.lt.faculty.app.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.presentation.components.EventComponent
import eif.viko.lt.faculty.app.presentation.components.FilledButtonComponent
import eif.viko.lt.faculty.app.presentation.events.SavedEventsEvent
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.util.UiEvent
import eif.viko.lt.faculty.app.presentation.viewModels.SavedEventsViewModel

@Composable
fun SavedEventsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SavedEventsViewModel = hiltViewModel()
) {
    val events = viewModel.events.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Primary
    ){
        Box (
            contentAlignment = Alignment.TopCenter
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = "Saved events",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 24.dp)
                )
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    if (events.value.isNotEmpty()){
                        items(events.value){ event ->
                            EventComponent(
                                event = event,
                                modifier = Modifier
                                    .size(300.dp, 170.dp)
                                    .clickable {
                                        viewModel.onEvent(SavedEventsEvent.OnEventClick(event))
                                    }
                                    .shadow(
                                        elevation = 18.dp,
                                        shape = RoundedCornerShape(16.dp),
                                    )
                                    .background(
                                        color = Primary,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .border(
                                        border = BorderStroke(2.dp, DarkerGreen),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                            )
                        }
                    } else {
                        item{
                            Text(
                                text = "No events were saved",
                                fontFamily = FontFamily.Serif,
                                fontSize = 20.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(16.dp)
        ){
            FilledButtonComponent(
                onClick = { viewModel.onEvent(SavedEventsEvent.OnBackClick)},
                buttonText = "Back"
            )
        }
    }
}