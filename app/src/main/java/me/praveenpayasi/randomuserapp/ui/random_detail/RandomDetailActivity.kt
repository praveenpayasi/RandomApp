package me.praveenpayasi.randomuserapp.ui.random_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.databinding.ActivityUserDetailBinding

class RandomDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<Result>("user")

        user?.let {
            val fullName = "${it.name.first} ${it.name.last}"
            binding.fullNameTextView.text = fullName

            val address =
                "${it.location.street.number}, ${it.location.street.name}, ${it.location.city}, ${it.location.state}, ${it.location.country}"
            binding.addressTextView.text = address

            binding.phoneTextView.text = it.phone

            Glide.with(this)
                .load(it.picture.large)
                .transform(CircleCrop())
                .into(binding.profileImageView)
        }
    }
}