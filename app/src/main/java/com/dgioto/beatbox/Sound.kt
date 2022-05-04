package com.dgioto.beatbox

private const val MP3 = ".mp3"

//добавляем в конструктор каждому загружаемому звуку собственный индификатор
class Sound(val assetPath: String, var soundId: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(MP3)
}