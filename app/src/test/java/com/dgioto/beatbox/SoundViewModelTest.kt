package com.dgioto.beatbox

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

    private lateinit var beatBox: BeatBox
    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel

    @Before
    fun setUp() {
        //создаем имитацию BeatBox
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        //subject - это тестируемый объект
        subject = SoundViewModel(beatBox)
        subject.sound = sound
    }

    @Test
    fun exposesSoundNameAsTitle() {
        //проверяем утверждение, что свойство заголовка этого субъекта будет тем же, что и имя звука
        assertThat(subject.title, `is`(sound.name))
    }

    @Test
    fun callsBeatBoxPlayOnButtonClicked() {
        subject.onButtonClicked()

        //проверяем что функция play() была вызвана для batBox с передачей sound в качестве параметра
        verify(beatBox).play(sound)
    }
}