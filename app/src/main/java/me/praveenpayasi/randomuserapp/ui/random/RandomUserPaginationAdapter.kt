package me.praveenpayasi.randomuserapp.ui.random

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.databinding.RandomUserItemLayoutBinding
import me.praveenpayasi.randomuserapp.ui.random_detail.RandomDetailActivity

class RandomUserPaginationAdapter :
    PagingDataAdapter<Result, RandomUserPaginationAdapter.DataViewHolder>(UIMODEL_COMPARATOR) {
    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            val name = "${result.name?.title} ${result.name?.first} ${result.name?.last}"
            binding.textViewName.text = name
            val address = "${result.location?.street?.number} ${result.location?.street?.name}, " +
                    "${result.location?.city}, ${result.location?.state}, ${result.location?.country}"
            binding.textViewAddress.text = address

            Glide.with(binding.imageViewProfile.context)
                .load(result.picture?.large)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        RandomUserItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.name?.first == newItem.name?.first
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}