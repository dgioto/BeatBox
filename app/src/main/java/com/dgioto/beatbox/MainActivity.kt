package com.dgioto.beatbox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding

//Филипс_Android.Программирование для проффесионалов_page_410

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox

    lateinit var s1: Array<String>
    //присваивываем переменной массив фотографий животных
    private val images: Array<Int> = arrayOf(R.drawable.baran, R.drawable.volk, R.drawable.gus,
        R.drawable.induk, R.drawable.koza, R.drawable.komar, R.drawable.korova, R.drawable.koshka,
        R.drawable.kukushka, R.drawable.kurica, R.drawable.lev, R.drawable.loshad,
        R.drawable.mish, R.drawable.petuh, R.drawable.ptiza, R.drawable.sviniya,
        R.drawable.slon, R.drawable.sobaka, R.drawable.utka, R.drawable.homyak)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            //Звуки из BeatBox передаются функции onCreate(Bundle?)
            adapter = SoundAdapter(beatBox.sounds, s1, images)
        }

        //присваиваем переменной массив имен животных
        s1 = resources.getStringArray(R.array.animals)
    }

    override fun onDestroy() {
        super.onDestroy()

        beatBox.release()
    }

    private inner class SoundHolder(
        private val binding: ListItemSoundBinding,
        val myText: TextView = itemView.finfViewById(R.id.myText),
        val myImage: ImageView = itemView.finfViewById(R.id.myImageView)) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            //Подключение модели представления
            binding.viewModel = SoundViewModel(beatBox)
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
        private val s1: Array<String>,
        private val img: Array<Int>
    ) : RecyclerView.Adapter<SoundHolder>() {

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

            holder.myText.text = s1[position]
            holder.myImage.setImageResource(img[position])
        }
    }


}