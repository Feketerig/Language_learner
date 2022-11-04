package hu.bme.aut.android.languagelearner.data.network

class WordApiImpl: WordApi {
    override suspend fun getAllWordSets(): List<WordSetDTO> {
        return listOf(
            WordSetDTO(
                id = 1,
                title = "Title",
                description = "Description - optional",
                words = listOf(WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(10,"angol"), WordSetTagDTO(9,"magyar"))
            ),
            WordSetDTO(
                id = 2,
                title = "Title 2",
                description = "Description - optional",
                words = listOf(WordPairDTO(2,"angol", "english", true), WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(5,"német"), WordSetTagDTO(8,"magyar"))
            ),
            WordSetDTO(
                id = 3,
                title = "Title 3",
                description = "Description - optional",
                words = listOf(WordPairDTO(1,"magyar", "hungarian", false)),
                tags = listOf(WordSetTagDTO(1,"angol"), WordSetTagDTO(2,"magyar"))
            )
        )
    }

    override suspend fun updateWordMemorized(wordPairId: Int, memorized: Boolean) {

    }
}