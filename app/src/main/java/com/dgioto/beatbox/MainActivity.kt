package com.dgioto.beatbox

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

//Филипс_Android.Программирование для проффесионалов_page_410

private const val MAX_SOUNDS = 1

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var beatBoxLoad: BeatBoxLoad
    private lateinit var soundPool: SoundPool

    //присваивываем переменной массив фотографий животных
    private val images: Array<String> = arrayOf(
        "baran.jpg",
        "volk.jpg",
        "gus.jpg",
        "induk.jpg",
        "koza.jpg",
        "komar.jpg",
        "korova.jpg",
        "koshka.jpg",
        "kukushka.jpg",
        "kurica.jpg",
        "lev.jpg",
        "loshad.jpg",
        "mish.jpg",
        "petuh.jpg",
        "ptiza.jpg",
        "sverchek.jpg",
        "sviniya.jpg",
        "slon.jpg",
        "sobaka.jpg",
        "utka.jpg",
        "homyak.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Создание объекта SoundPool для воспроизведения звуков
        soundPool = SoundPool.Builder()
            .setMaxStreams(MAX_SOUNDS)
            .build()

        beatBox = BeatBox(soundPool)
        beatBoxLoad = BeatBoxLoad(assets, soundPool)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            //Звуки из BeatBox передаются функции onCreate(Bundle?)
            adapter = SoundAdapter(beatBoxLoad.sounds, images)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    private inner class SoundHolder(
        private val binding: ListItemSoundBinding) : RecyclerView.ViewHolder(binding.root) {

        val myImage: ImageButton = itemView.findViewById(R.id.imageButton)

        init {
            //Подключение модели представления
            binding.apply {
                viewModel = SoundViewModel(beatBox).apply {
                    view = itemView // Присвоение значения view
                }
            }
        }

        //Подключение модели представления
        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                //Приказ макету обновить себя немедленно
                executePendingBindings()
            }
        }
    }

    //Связываем SoundAdapter со списком объектов Sound
    private inner class SoundAdapter(
        private val sound: List<Sound>,
        private val img: Array<String>
    ) : RecyclerView.Adapter<SoundHolder>() {

        override fun getItemCount() = sound.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )

            binding.lifecycleOwner = this@MainActivity

            return  SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sound[position]
            holder.bind(sound)

            // Добавляем в кнопку картинку по ее порядковому номеру в массиве
            // Загружаем изображение из папки assets с помощью Picasso и устанавливаем его в ImageButton
            val imageName = img[position]
            val imagePath = "file:///android_asset/animals_pictures/$imageName"

            // Загружаем изображение и растягиваем его на всю кнопку
            val picasso: RequestCreator = Picasso.get().load(imagePath)
            picasso.fit().centerCrop().into(holder.myImage)
        }
    }
}