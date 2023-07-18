package com.dgioto.beatbox

import android.os.Handler
import android.os.Looper
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
            //object properties updated
            title.postValue(sound?.name)
        }

    fun onButtonClicked() {
        //sound playback
        sound?.let {
            beatBox.play(it)
        }

        //Button animation to increase its size
        val animator = animate(view!!)
            .scaleX(1.5f)
            .scaleY(1.5f)
            .setDuration(1000)
            .withEndAction {
                //Delay before decreasing the button by 2 seconds
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