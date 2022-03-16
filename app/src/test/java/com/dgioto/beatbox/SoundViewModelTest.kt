package com.dgioto.beatbox

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`

import org.junit.Before
import org.junit.Test

class SoundViewModelTest {

    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel

    @Before
    fun setUp() {
        sound = Sound("assetPath")
        //subject - это тестируемый объект
        subject = SoundViewModel()
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        //проверяем утверждение, что свойство заголовка этого субъекта будет тем же, что и имя звука
        assertThat(subject.title, `is`(sound.name))
    }
}