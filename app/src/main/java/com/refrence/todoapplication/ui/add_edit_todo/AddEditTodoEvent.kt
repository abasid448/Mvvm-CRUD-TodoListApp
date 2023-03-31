package com.refrence.todoapplication.ui.add_edit_todo

sealed class AddEditTodoEvent{
    data class OnTitleChange(val title :String): AddEditTodoEvent()
    data class OnDescriptionChanges(val description : String):AddEditTodoEvent()
    object OnSaveTodoClick:AddEditTodoEvent()

}
