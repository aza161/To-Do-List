package aza161.test.todolist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform