package com.dgioto.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import android.widget.Button
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "animals_sounds"
private const val MAX_SOUNDS = 1

class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>
    //Создание объекта SoundPool для воспроизведения звуков в BeatBox
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init {
        sounds = loadSounds()
    }

    //воспроизведение звуков
    fun play(sound: Sound){
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    //освобождение SoundPool
    fun release(){
        soundPool.release()
    }

    //получить список активов
    private fun loadSounds(): List<Sound>{

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUND_FOLDER)!!
        } catch (e: Exception){
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        //строим список объектов Sound
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUND_FOLDER/$filename"
            val sound = Sound(assetPath)
            //загрузка всех звуков
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }
        return sounds
    }

    //загрузка звуков в SoundPool
    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        //soundPool.load(afd, 1) загружаем файл в SoundPool для последующего воспроизведения
        //soundPool.load(...) возвращает индификатор типа int
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}