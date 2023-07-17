package com.dgioto.beatbox

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
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
import java.io.InputStream

//Филипс_Android.Программирование для проффесионалов_page_410

private const val MAX_SOUNDS = 1

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var beatBoxLoad: BeatBoxLoad
    private lateinit var soundPool: SoundPool

    //присваивываем переменной массив фотографий животных
    private val images: Array<String> = arrayOf(
        "animals_pictures/baran.jpg",
        "animals_pictures/volk.jpg",
        "animals_pictures/gus.jpg",
        "animals_pictures/induk.jpg",
        "animals_pictures/koza.jpg",
        "animals_pictures/komar.jpg",
        "animals_pictures/korova.jpg",
        "animals_pictures/koshka.jpg",
        "animals_pictures/kukushka.jpg",
        "animals_pictures/kurica.jpg",
        "animals_pictures/lev.jpg",
        "animals_pictures/loshad.jpg",
        "animals_pictures/mish.jpg",
        "animals_pictures/petuh.jpg",
        "animals_pictures/ptiza.jpg",
        "animals_pictures/sverchek.jpg",
        "animals_pictures/sviniya.jpg",
        "animals_pictures/slon.jpg",
        "animals_pictures/sobaka.jpg",
        "animals_pictures/utka.jpg",
        "animals_pictures/homyak.jpg")

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

            //добавляем в кнопку картинку по ее порядковому номеру в массиве
            val imagePath = img[position]
            val inputStream: InputStream = holder.itemView.context.assets.open(imagePath)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val drawable = BitmapDrawable(holder.itemView.context.resources, bitmap)
            holder.myImage.setImageDrawable(drawable)
        }
    }
}