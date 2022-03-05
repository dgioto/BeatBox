package com.dgioto.beatbox

class SoundViewModel {

    var sound: Sound? = null
        set(sound) {field = sound}

    //добавление функций привязки
    val title: String?
    get() = sound?.name
}