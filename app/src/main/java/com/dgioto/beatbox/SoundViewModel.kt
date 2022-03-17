package com.dgioto.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            //свойства объектов обновлены
            notifyChange()
        }

    @get:Bindable
    //добавление функций привязки
    val title: String?
        get() = sound?.name
}