/**
 * @author aza161
 *
 * A serializer object for UUID. This custom serializer ensures that UUIDs
 * can be serialized to and deserialized from strings using kotlinx.serialization.
 *
 * Example:
 * ```
 * @Serializable
 * data class Example(val id: @Serializable(with = UuidSerializer::class) Uuid)
 * ```
 *
 * @see [kotlinx.serialization.KSerializer]
 */

package aza161.test.todolist.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object UuidSerializer : KSerializer<Uuid> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uuid {
        return try {
            Uuid.parse(decoder.decodeString())
        } catch (e: Exception) {
            throw SerializationException("Invalid UUID format", e)
        }
    }

    override fun serialize(encoder: Encoder, value: Uuid) {
        encoder.encodeString(value.toString())
    }

}