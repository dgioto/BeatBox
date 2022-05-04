package com.dgioto.beatbox

import android.graphics.Picture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding

//page 433

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            //Звуки из BeatBox передаются функции onCreate(Bundle?)
            adapter = SoundAdapter(beatBox.sounds)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        beatBox.release()
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root){

        private var picture: Int = 0
        private var name = ""
        private lateinit var sound: Sound
        private var button: Button = itemView.findViewById(R.id.button)

            init {
                //Подключение модели представления
                binding.viewModel = SoundViewModel(beatBox)
                //Установка рисунка на кнопку
                name = sound.name
                picture = when(name) {
                    "Волк" -> R.drawable.volk
                    "Гуси" -> R.drawable.gus
                    else -> 0
                }
                button.setBackgroundResource(picture)
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

    //Связываем SoundAdapter сосписком объектов Sound
    private inner class SoundAdapter(private val sound: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {

        override fun getItemCount() = sound.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return  SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sound[position]
            holder.bind(sound)
        }
    }


}