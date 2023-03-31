package com.refrence.todoapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.refrence.todoapplication.data.Todo
import com.refrence.todoapplication.data.TodoRepository
import com.refrence.todoapplication.util.Routes
import com.refrence.todoapplication.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewmodel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun OnEvent(events: TodoListEvents) {
        when (events) {
            is TodoListEvents.OnTodoClick -> {
                sendUiEvent(UiEvents.Navigate(Routes.ADD_EDIT_TODO + "?todoId = ${events.todo.id}"))
            }
            is TodoListEvents.OnAddTodoClick -> {
                sendUiEvent(UiEvents.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvents.OnUndDeleteClidk -> {
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
            is TodoListEvents.OnDeleteTodoCLick -> {
                viewModelScope.launch {

                    deletedTodo = events.todo
                    repository.deleteTodo(events.todo)
                    sendUiEvent(
                        UiEvents.ShowSnackbar(
                            message = "Todo Deleted",
                            action = "undo"
                        )
                    )
                }
            }
            is TodoListEvents.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        events.todo.copy(
                            isDone = events.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(events: UiEvents) {
        viewModelScope.launch {
            _uiEvent.send(events)
        }
    }
}