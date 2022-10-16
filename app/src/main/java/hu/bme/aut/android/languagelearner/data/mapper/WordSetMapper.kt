package hu.bme.aut.android.languagelearner.data.mapper

import hu.bme.aut.android.languagelearner.data.local.PopulatedWordSet
import hu.bme.aut.android.languagelearner.data.local.WordPairEntity
import hu.bme.aut.android.languagelearner.domain.model.WordPair
import hu.bme.aut.android.languagelearner.domain.model.WordSet

fun PopulatedWordSet.toDomain() = WordSet(
    id = wordSet.id,
    title = wordSet.title,
    description = wordSet.description,
    words = words.map(WordPairEntity::toDomain),
    tags = tags.map { it.tag },
)

fun WordPairEntity.toDomain() = WordPair(
    id = id,
    first = first,
    second = second,
    memorized = memorized
)