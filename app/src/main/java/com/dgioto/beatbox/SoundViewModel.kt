package com.dgioto.beatbox

import android.view.View
import androidx.core.view.ViewCompat.animate
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox) {

    val title: MutableLiveData<String?> = MutableLiveData()
    var isImageScaled = false
    var view: View? = null

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
        if (!isImageScaled) animate(view!!).scaleX(1.1f).scaleY(1.1f).setDuration(500);
        if (isImageScaled) animate(view!!).scaleX(1f).scaleY(1f).setDuration(500);
        isImageScaled = !isImageScaled;
    }
}