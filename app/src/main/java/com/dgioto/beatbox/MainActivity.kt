package com.dgioto.beatbox

import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

//Phillips B., Stewart Ch. - Android Programming: The Big Nerd Ranch Guide, page 410

private const val MAX_SOUNDS = 1

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var beatBoxLoad: BeatBoxLoad
    private lateinit var soundPool: SoundPool

    //assign an array of animal photos to a variable
    private val images: Array<String> = arrayOf(
        "bird.jpg",
        "cat.jpg",
        "chicken.jpg",
        "cow.jpg",
        "cricket.jpg",
        "cuckoo.jpg",
        "dog.jpg",
        "duck.jpg",
        "elephant.jpg",
        "goat.jpg",
        "goose.jpg",
        "hamster.jpg",
        "horse.jpg",
        "lion.jpg",
        "mosquito.jpg",
        "mouse.jpg",
        "pig.jpg",
        "ram.jpg",
        "rooster.jpg",
        "turkey.jpg",
        "wolf.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide the navigation bar and set the activity to full screen
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        //Creating a SoundPool object to play sounds
        soundPool = SoundPool.Builder()
            .setMaxStreams(MAX_SOUNDS)
            .build()

        beatBox = BeatBox(soundPool)
        beatBoxLoad = BeatBoxLoad(assets, soundPool)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            //Sounds from the BeatBox are passed to the onCreate(Bundle?) function
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
            //Connecting the view model
            binding.apply {
                viewModel = SoundViewModel(beatBox).apply {
                    view = itemView //Assigning a value to a view
                }
            }
        }

        //Connecting the view model
        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                //Telling the layout to update itself immediately
                executePendingBindings()
            }
        }
    }

    //Associating the SoundAdapter with a list of Sound objects
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

            // Add an image to the button by its serial number in the array
            // Load image from assets folder with Picasso and set it to ImageButton
            val imageName = img[position]
            val imagePath = "file:///android_asset/animals_pictures/$imageName"

            // Upload an image and stretch it to fit the entire button
            val picasso: RequestCreator = Picasso.get().load(imagePath)
            picasso.fit().centerCrop().into(holder.myImage)
        }
    }
}