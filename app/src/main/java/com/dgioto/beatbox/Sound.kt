package com.dgioto.beatbox

private const val MP3 = ".mp3"

//add a custom identifier to the constructor for each loaded sound
class Sound(val assetPath: String, var soundId: Int? = null) {

    val name = assetPath.split("/").last().removeSuffix(MP3)
}