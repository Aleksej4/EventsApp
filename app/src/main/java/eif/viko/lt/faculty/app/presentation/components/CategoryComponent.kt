package eif.viko.lt.faculty.app.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import eif.viko.lt.faculty.app.presentation.ui.theme.DarkerGreen
import eif.viko.lt.faculty.app.presentation.ui.theme.LightGray
import eif.viko.lt.faculty.app.presentation.ui.theme.Primary
import eif.viko.lt.faculty.app.presentation.ui.theme.Secondary
import eif.viko.lt.faculty.app.presentation.viewModels.CategoryViewModel
import eif.viko.lt.faculty.app.presentation.viewModels.CreateEventViewModel

@Composable
fun CategoryComponent(
    viewModel: CategoryViewModel = hiltViewModel(),
    onCategorySelected: (String) -> Unit,
    category: String,
    onDialogClose: (Boolean) -> Unit
){
    Box(
        modifier = Modifier
            .size(width = 330.dp, height = 400.dp)
            .background(
                color = LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(16.dp)
            ),
    ){
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Choose category",
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .width(330.dp)
                    .height(280.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ){
                items(viewModel.categoriesList.orEmpty()){categoryData ->
                    val backgroundColor = if(category == categoryData){
                        DarkerGreen
                    }else{
                        LightGray
                    }
                    CategoryOption(
                        category = categoryData,
                        modifier = Modifier
                            .clickable { onCategorySelected(categoryData) }
                            .background(
                                color = backgroundColor,
                                shape = RoundedCornerShape(16.dp)
                            )

                    )
                }
            }
            Box (
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                FilledButtonComponent(
                    onClick = { onDialogClose(false) },
                    buttonText = "Close"
                )
            }
        }
    }
}

@Composable
fun CategoryOption(
    category: String,
    modifier: Modifier
){
    Box (
        modifier = modifier
            .size(width = 280.dp, height = 35.dp)
            .border(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color.Black)
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = category,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif
        )
    }
}