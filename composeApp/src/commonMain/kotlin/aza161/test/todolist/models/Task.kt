package aza161.test.todolist.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class Task(
    var name: String? = null,
    var description: String? = null,
    var dueDate: LocalDateTime? = null,
    var priority: Int = 0
) : Comparable<Task> {

    public var complete: Boolean = false
    public var remindeDateTime: LocalDateTime? = null

    @OptIn(ExperimentalUuidApi::class)
    public val id: Uuid = Uuid.random()

    @Transient
    public val subTasks: MutableList<Task> = mutableListOf()

    @Serializable(with = InstantIso8601Serializer::class)
    private val timestamp: Instant = Clock.System.now()
    
    override fun compareTo(other: Task): Int {
        return when {
            (this.priority == other.priority) -> this.timestamp.compareTo(other.timestamp)
            else -> this.priority.compareTo(other.priority)
        }
    }
}