package com.dgioto.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox) {

    val title: MutableLiveData<String?> = MutableLiveData()

    var sound: Sound? = null
        set(sound) {
            field = sound
            //свойства объектов обновлены
            title.postValue(sound?.name)
        }

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }
}