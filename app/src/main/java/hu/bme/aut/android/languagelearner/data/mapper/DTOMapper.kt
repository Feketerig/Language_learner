package hu.bme.aut.android.languagelearner.data.mapper

import hu.bme.aut.android.languagelearner.data.local.WordPairEntity
import hu.bme.aut.android.languagelearner.data.local.WordSetEntity
import hu.bme.aut.android.languagelearner.data.network.dto.WordPairDTO
import hu.bme.aut.android.languagelearner.data.network.dto.WordSetDTO

fun WordSetDTO.toDatabase(): WordSetEntity =
    WordSetEntity(
        id = id,
        title = name,
        description = description,
        deadline = deadline.toEpochMilliseconds()
    )

fun WordPairDTO.toDatabase(): WordPairEntity =
    WordPairEntity(
        id = id,
        first = word,
        second = translation,
        memorized = saved
    )