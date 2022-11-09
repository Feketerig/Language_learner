package hu.bme.aut.android.languagelearner.data.network.dto

import io.ktor.util.*
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


@Serializable
data class WordSetDTO (
    val id: Int,
    val name: String,
    val description: String,
    @Serializable(InstantSerializer::class)
    val deadline: Instant,
    val metadata: List<String>
)

object InstantSerializer : KSerializer<Instant> {
    override fun deserialize(decoder: Decoder): Instant =
        decoder.decodeString().toInstant()

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Instant",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: Instant) =
        encoder.encodeString(value.toString())
}