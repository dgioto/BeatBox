package com.dgioto.beatbox

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.dgioto.beatbox.databinding.ActivityMainBinding
import com.dgioto.beatbox.databinding.ListItemSoundBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

//Phillips B., Stewart Ch. - Android Programming: The Big Nerd Ranch Guide, page 410

private const val MAX_SOUNDS = 1

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var beatBox: BeatBox
    private lateinit var beatBoxLoad: BeatBoxLoad
    private lateinit var soundPool: SoundPool

    //assign an array of animal photos to a variable
    private val images: Array<String> = arrayOf(
        "01_bird.jpg",
        "02_chicken.jpg",
        "03_crow.jpg",
        "04_cuckoo.jpg",
        "05_duck.jpg",
        "06_goose.jpg",
        "07_owl.jpg",
        "08_rooster.jpg",
        "09_turkey.jpg",
        "21_cricket.jpg",
        "22_mosquito.jpg",
        "41_dolphin.jpg",
        "61_cat.jpg",
        "62_cow.jpg",
        "63_dog.jpg",
        "64_donkey.jpg",
        "65_elephant.jpg",
        "66_goat.jpg",
        "67_hamster.jpg",
        "68_horse.jpg",
        "69_lion.jpg",
        "70_monkey.jpg",
        "71_mouse.jpg",
        "72_pig.jpg",
        "73_ram.jpg",
        "74_snake.jpg",
        "75_wolf.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide the navigation bar and set the activity to full screen
        @Suppress("DEPRECATION")
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
            //Sounds from the BeatBox are passed to the onCreate(Bundle?) function
            adapter = SoundAdapter(beatBoxLoad.sounds, images)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.qr_code -> {
                showQRCodeDialog()
                true
            }
            R.id.share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, R.string.share_google_play)
                startActivity(Intent.createChooser(intent, ""))
                true
            }
            R.id.rate_the_app -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.google_play_url))
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showQRCodeDialog() {
        // Create a AlertDialog to show the QR code image
        val qrCodeImage = R.drawable.qr_code
        val qrCodeBitmap = BitmapFactory.decodeResource(resources, qrCodeImage)

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_qr_code, null)
        val qrCodeImageView = dialogView.findViewById<ImageView>(R.id.qrCodeImageView)
        qrCodeImageView.setImageBitmap(qrCodeBitmap)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle(getString(R.string.qr_code))
        builder.setMessage(getString(R.string.qr_code_text))
        builder.setPositiveButton("ОК") { dialog, _ -> dialog.dismiss() }
        builder.show()
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