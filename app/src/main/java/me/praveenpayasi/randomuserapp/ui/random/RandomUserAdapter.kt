package me.praveenpayasi.randomuserapp.ui.random

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.databinding.RandomUserItemLayoutBinding

class RandomUserAdapter(
    private val articleList: ArrayList<Result>
) : RecyclerView.Adapter<RandomUserAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Result) {
            "${article.name.title} ${article.name.first} ${article.name.last}".also {
                binding.textViewTitle.text = it
            }
            "${article.location.city}${article.location.state} ${article.location.country}".also {
                binding.textViewDescription.text = it
            }
            binding.textViewSource.text = ""
            Glide.with(binding.imageViewBanner.context)
                .load(article.picture.large)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {

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