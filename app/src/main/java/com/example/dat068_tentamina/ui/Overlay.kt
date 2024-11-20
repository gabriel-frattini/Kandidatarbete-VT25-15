package com.example.dat068_tentamina.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dat068_tentamina.R
import com.example.dat068_tentamina.viewmodel.TentaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overlay(viewModel: TentaViewModel) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                //The content of the menu
                MenuScreen(modifier = Modifier,viewModel)

            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Question ${viewModel.getCurrentQuestionNr()}") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        } }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.textMode.value = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.text),
                                contentDescription = "text",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        IconButton(onClick = {
                        }) {
                            SizePicker(viewModel, "eraser")
                        }
                        IconButton(onClick = {}) {
                            SizePicker(viewModel)
                        }
                        IconButton(onClick = {
                            viewModel.pop()
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                )
            },
        )
        { contentPadding ->
            ExamScreen(modifier = Modifier.padding(contentPadding), viewModel)
        }
    }
}
@Composable
        fun ExamScreen(modifier: Modifier = Modifier, viewModel: TentaViewModel ) {
            Column {
                Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                    DrawingScreen(viewModel)
                }
            }
        }
@Composable
fun MenuScreen(modifier: Modifier = Modifier, viewModel: TentaViewModel){

    Column(

        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxHeight()
            .requiredWidth(500.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(100.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
                IconButton(
                    onClick = { println("Hejsan") /*TODO: An actual information page with user info and user guide???*/},
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .align(alignment = Alignment.CenterHorizontally)

                ) {
                    Icon(
                        Icons.Filled.Info, contentDescription = "Information",
                        modifier = Modifier
                            .size(100.dp,100.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                }
        }
        for((key,value ) in viewModel.questions )
        {
            Card (
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(100.dp)
                    .align(alignment = Alignment.CenterHorizontally)

            ){
                Button(
                    onClick = {
                        println("Bytte fråga till $key ")
                        viewModel.changedQuestion(key)
                              },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .align(alignment = Alignment.CenterHorizontally)

                ) {
                    Text(
                        text = "Question $key",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(alignment = Alignment.CenterVertically)
                    )


                }
            }
        }
    }
}