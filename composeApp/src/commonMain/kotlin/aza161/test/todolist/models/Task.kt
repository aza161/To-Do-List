package aza161.test.todolist.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi


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

    public var priority: Int = 0
        set(priority: Int) {
            require(priority in 0..10) { "Priority should be between 0 and 10, priority = $priority" }
            field = priority
        }

    override fun compareTo(other: Task): Int {
        return when {
            (this.priority == other.priority) -> this.timestamp.compareTo(other.timestamp)
            else -> this.priority.compareTo(other.priority)
        }
    }
}