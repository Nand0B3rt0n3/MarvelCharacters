package es.nandobertone.marvelcharacters.ui.CharactersList

import es.nandobertone.marvelcharacters.domain.model.Character

data class MarvelListState(
    val isLoading : Boolean = false,
    val characterList : List<Character> = emptyList(),
    val error : String = ""
)
