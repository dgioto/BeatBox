package com.dgioto.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "animals_sounds"

class BeatBoxLoad(private val assets: AssetManager, private val soundPool: SoundPool) {

    val sounds: List<Sound>

    init {
        sounds = loadSounds()
    }

    //get a list of assets
    private fun loadSounds(): List<Sound>{

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUND_FOLDER) ?: emptyArray()
        } catch (e: Exception){
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        //building a list of Sound objects
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUND_FOLDER/$filename"
            val sound = Sound(assetPath)
            //download all sounds
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }
        return sounds
    }

    //uploading sounds to SoundPool
    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        try {
            val soundId = soundPool.load(afd, 1)
            sound.soundId = soundId
        } finally {
            afd.close()
        }
    }
}