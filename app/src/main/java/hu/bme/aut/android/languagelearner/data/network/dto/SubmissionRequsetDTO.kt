package hu.bme.aut.android.languagelearner.data.network.dto

@kotlinx.serialization.Serializable
data class SubmissionRequestDTO (
    val courseId: Int,
    val score: Int
)