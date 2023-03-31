package com.refrence.todoapplication.ui.list

import com.refrence.todoapplication.data.Todo

sealed class TodoListEvents{
    data class OnDeleteTodoCLick(val todo: Todo): TodoListEvents()
    data class OnDoneChange(val todo: Todo,val isDone : Boolean):TodoListEvents()
    object OnUndDeleteClidk:TodoListEvents()
    data class OnTodoClick (val todo: Todo): TodoListEvents()
    object OnAddTodoClick : TodoListEvents()
}
