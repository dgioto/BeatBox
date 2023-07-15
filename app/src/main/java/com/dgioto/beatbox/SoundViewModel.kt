package com.dgioto.beatbox

import android.view.View

import androidx.core.view.ViewCompat.animate
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox: BeatBox) {

    val title: MutableLiveData<String?> = MutableLiveData()
    private var isImageScaled = false
    var view: View? = null

    var sound: Sound? = null
        set(sound) {
            field = sound
            //свойства объектов обновлены
            title.postValue(sound?.name)
        }

    fun onButtonClicked() {
        //проигрыввание звука
        sound?.let {
            beatBox.play(it)
        }

        //Анимация кнопки для увеличения ее размера
        val animator = animate(view!!)
            .scaleX(1.5f)
            .scaleY(1.5f)
            .setDuration(1000)
            .withEndAction {
                    animate(view!!)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(1000)
                        .interpolator = FastOutSlowInInterpolator()
            }
        animator.start()
        isImageScaled = !isImageScaled
    }
}