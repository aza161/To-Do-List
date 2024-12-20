package aza161.test.todolist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "To-Do List",
    ) {
        App()
    }
}