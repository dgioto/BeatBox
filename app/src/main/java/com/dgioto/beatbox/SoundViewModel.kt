package com.dgioto.beatbox

import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox, private val imageButton: ImageButton) {

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
        // Загрузить анимацию из XML-файла
        val scaleAnimation = AnimationUtils.loadAnimation(imageButton.context, R.anim.scale_animation)
        // Применить анимацию к кнопке
        imageButton.startAnimation(scaleAnimation)
    }
}