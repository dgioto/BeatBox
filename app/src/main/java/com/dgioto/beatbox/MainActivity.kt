package com.dgioto.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding

//page 427

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

    private inner class SoundHolder(binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root)

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

        override fun onBindViewHolder(holder: SoundHolder, position: Int) { }
    }


}