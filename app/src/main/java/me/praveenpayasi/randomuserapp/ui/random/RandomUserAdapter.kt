package me.praveenpayasi.randomuserapp.ui.random

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.databinding.RandomUserItemLayoutBinding
import me.praveenpayasi.randomuserapp.ui.random_detail.RandomDetailActivity

class RandomUserAdapter(
    private val articleList: ArrayList<Result>
) : RecyclerView.Adapter<RandomUserAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            val name = "${result.name.title} ${result.name.first} ${result.name.last}"
            binding.textViewName.text = name

            val address =
                "${result.location.street.number} ${result.location.street.name}, ${result.location.city}, ${result.location.state}, ${result.location.country}"
            binding.textViewAddress.text = address

            Glide.with(binding.imageViewProfile.context)
                .load(result.picture.large)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imageViewProfile)

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, RandomDetailActivity::class.java)
                intent.putExtra("user", result)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            RandomUserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articleList[position])

    fun updateData(list: List<Result>) {
        articleList.clear()
        articleList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        articleList.clear()
        notifyDataSetChanged()
    }

}