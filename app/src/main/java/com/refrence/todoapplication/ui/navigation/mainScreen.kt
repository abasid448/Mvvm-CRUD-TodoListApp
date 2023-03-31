package com.refrence.todoapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.refrence.todoapplication.ui.add_edit_todo.AddEditTodoScreen
import com.refrence.todoapplication.ui.list.TodoListScreen
import com.refrence.todoapplication.util.Routes

@Composable
fun mainScreen(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Routes.TODO_LIST){
        composable(Routes.TODO_LIST) {
            TodoListScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(
            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
            arguments = listOf(
                navArgument(name = "todoId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditTodoScreen(onPopBackStack = {
                navController.popBackStack()
            })
        }
    }
}
