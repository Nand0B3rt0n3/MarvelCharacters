package es.nandobertone.marvelcharacters.domain.model

import es.nandobertone.marvelcharacters.data.data_source.dto.Thumbnail

data class Character(
    val id : Int,
    val name : String,
    val description : String,
    val thumbnail : String,
    val thumbnailExt: String,
    val comics : List<String>,
)