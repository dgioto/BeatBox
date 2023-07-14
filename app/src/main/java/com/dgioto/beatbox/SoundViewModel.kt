package com.dgioto.beatbox

import android.os.Handler
import android.os.Looper
import android.view.View

import androidx.core.view.ViewCompat.animate
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
                //Задержка перед уменьшением кнопки на 2 секунды
                Handler(Looper.getMainLooper()).postDelayed({
                    animate(view!!)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(1000)
                        .interpolator = FastOutSlowInInterpolator()
                }, 1000)
            }
        animator.start()
        isImageScaled = !isImageScaled
    }
}