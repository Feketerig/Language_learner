package hu.bme.aut.android.languagelearner.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)