package com.example.dat068_tentamina.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dat068_tentamina.R
import com.example.dat068_tentamina.viewmodel.TentaViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.dat068_tentamina.viewmodel.ExamInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Overlay(viewModel: TentaViewModel, examInfo: ExamInfo) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                //The content of the menu
                MenuScreen(modifier = Modifier,viewModel,examInfo)

            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Question ${viewModel.currentQuestion.intValue} ") },
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
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()) {
            DrawingScreen(viewModel)
        }
    }
}
@Composable
fun MenuScreen(modifier: Modifier = Modifier, viewModel: TentaViewModel, examInfo: ExamInfo){
    val scrollState = rememberScrollState()
    var showInfoDialog by remember { mutableStateOf(false) }

    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {Text("Student Information")},
            text = {Text("${examInfo.getName()}\n${examInfo.getPersonalNumber()} \n${examInfo.getAnonymousCode()}")},
            confirmButton = {},
            dismissButton = {}
        )

    }

    Column (
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxHeight()
            .verticalScroll(scrollState)
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
                    onClick = { showInfoDialog = true /*TODO: An actual information page with user info and user guide???*/},
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .align(alignment = Alignment.CenterHorizontally)

                ) {
                    Icon(
                        Icons.Filled.Info, contentDescription = "Information",
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                    )
                }
        }
        for((key,value) in viewModel.questions)
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
                        viewModel.changeQuestion(key)
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