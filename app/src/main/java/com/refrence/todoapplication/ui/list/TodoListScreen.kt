package com.refrence.todoapplication.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.refrence.todoapplication.util.UiEvents
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    onNavigate: (UiEvents.Navigate) -> Unit,
    viewmodel: TodoListViewmodel = hiltViewModel()
){
    val todos =viewmodel.todos.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewmodel.uiEvent.collect(){ event ->
            when(event){
                is UiEvents.ShowSnackbar -> {
                    val result =    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewmodel.OnEvent(TodoListEvents.OnUndDeleteClidk)
                    }
                }
                is UiEvents.Navigate -> onNavigate(event)
                else -> Unit
            }
        }

    }
    
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewmodel.OnEvent(TodoListEvents.OnAddTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add")
            }
        }
    ) {
      LazyColumn(
          modifier = Modifier.fillMaxSize()
      ){
          items(todos.value) { todo ->
              TodoItem(
                  todo = todo,
                  onEvent =viewmodel::OnEvent,
                  modifier = Modifier
                      .fillMaxSize()
                      .clickable {
                          viewmodel.OnEvent(TodoListEvents.OnTodoClick(todo))
                      }
                      .padding( 16.dp)
              )

          }
      }
    }
}