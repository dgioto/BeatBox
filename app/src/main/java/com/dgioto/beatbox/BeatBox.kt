package com.dgioto.beatbox

import android.media.SoundPool

class BeatBox(private val soundPool: SoundPool) {

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
}