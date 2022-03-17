package com.dgioto.beatbox

private const val WAV = ".wav"

//добавляем в конструктор каждому загружаемому звуку собственный индификатор
class Sound(val assetPath: String, var soundId: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(WAV)
}