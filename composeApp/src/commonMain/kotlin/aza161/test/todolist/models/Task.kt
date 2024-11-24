package aza161.test.todolist.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

/**
 * @author aza161
 *
 * A class that represents a Task in a ToDoList app.
 *
 * A task can have a name, description, due date, priority, and optional reminder.
 * Tasks are comparable based on their priority and timestamp.
 * @see [Comparable]
 * @see [compareTo]
 *
 * @property name Represents the name of the task.
 * @property description Represents the description of the task.
 * @property dueDate Represents the due date of the task.
 * @property complete Represents if the task is completed or not.
 * @property reminderDateTime Represents the reminder date and time of the task.
 * @property id Represents the unique universal id of the task.
 * @property subTasks Represents the list of subtasks of the task.
 * @property timestamp Represents the initialization timestamp of the task.
 * @property priority Represents the priority of the task. It ranges between 0 and 10.
 */
@Serializable
data class Task(
    var name: String? = null,
    var description: String? = null,
    var dueDate: LocalDateTime? = null,
) : Comparable<Task> {

    public var complete: Boolean = false
    public var reminderDateTime: LocalDateTime? = null

    @Serializable(with = UuidSerializer::class)
    @OptIn(ExperimentalUuidApi::class)
    public val id: Uuid = Uuid.random()

    public val subTasks: MutableList<Task> = mutableListOf()

    @Serializable(with = InstantIso8601Serializer::class)
    public val timestamp: Instant = Clock.System.now()

    /**
     * Sets the priority of the task.
     * The priority must be within the range of 0 to 10.
     * @param priority The priority to set, which should be between 0 (lowest) and 10 (highest).
     * @throws IllegalArgumentException if the priority is not within the valid range.
     */
    public var priority: Int = 0
        set(priority: Int) {
            require(priority in 0..10) { "Priority should be between 0 and 10, priority = $priority" }
            field = priority
        }

    /**
     * Compares this Task with the specified other Task for order.
     * The comparison is primarily based on the priority; if the priority is the same, the timestamp is compared.
     * @param other the Task to be compared.
     * @return zero if this Task is equal to the other Task, a negative number if it is less,
     * and a positive number if it is greater.
     */
    override fun compareTo(other: Task): Int {
        return when {
            (this.priority == other.priority) -> this.timestamp.compareTo(other.timestamp)
            else -> this.priority.compareTo(other.priority)
        }
    }
}