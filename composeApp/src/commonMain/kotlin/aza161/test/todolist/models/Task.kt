package aza161.test.todolist.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Task(
    var name: String? = null,
    var description: String? = null,
    var dueDate: LocalDateTime? = null,
    var priority: Int = 0
) : Comparable<Task> {
    public val id: Int = Task.nextID()

    @Transient
    private val subTasks: MutableList<Task> = mutableListOf()

    @Serializable(with = InstantIso8601Serializer::class)
    private val timestamp: Instant = Clock.System.now()
    public var complete: Boolean = false

    companion object {
        private var idCounter = 0

        fun nextID(): Int {
            return idCounter++
        }
    }

    fun addSubtask(subtask: Task): Unit {
        this.subTasks.add(subtask)
    }

    fun getSubtasks(): List<Task> {
        return this.subTasks.toList()
    }

    override fun compareTo(other: Task): Int {
        return when {
            (this.priority == other.priority) -> this.timestamp.compareTo(other.timestamp)
            else -> this.priority.compareTo(other.priority)
        }
    }
}