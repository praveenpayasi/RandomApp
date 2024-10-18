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

//class RandomUserAdapter(
//    private val articleList: ArrayList<Result>
//) : RecyclerView.Adapter<RandomUserAdapter.DataViewHolder>() {
//
//    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(result: Result) {
//            val name = "${result.name.title} ${result.name.first} ${result.name.last}"
//            binding.textViewName.text = name
//
//            val address =
//                "${result.location.street.number} ${result.location.street.name}, ${result.location.city}, ${result.location.state}, ${result.location.country}"
//            binding.textViewAddress.text = address
//
//            Glide.with(binding.imageViewProfile.context)
//                .load(result.picture.large)
//                .apply(RequestOptions.circleCropTransform())
//                .into(binding.imageViewProfile)
//
//            itemView.setOnClickListener {
//                val context = itemView.context
//                val intent = Intent(context, RandomDetailActivity::class.java)
//                intent.putExtra("user", result)
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        DataViewHolder(
//            RandomUserItemLayoutBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//
//    override fun getItemCount(): Int = articleList.size
//
//    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
//        holder.bind(articleList[position])
//
//    fun updateData(list: List<Result>) {
//        articleList.clear()
//        articleList.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    fun clearData() {
//        articleList.clear()
//        notifyDataSetChanged()
//    }
//
//}

//class RandomUserAdapter(
//    private val userList: MutableList<Result> = mutableListOf()
//) : PagingDataAdapter<Result, RandomUserAdapter.DataViewHolder>(UserDiffCallback()) {
//
//    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(result: Result) {
//            // Bind user name
//            binding.textViewName.text = "${result.name.title} ${result.name.first} ${result.name.last}"
//
//            // Bind user address using buildString for clarity
//            binding.textViewAddress.text = buildString {
//                append(result.location.street.number).append(" ")
//                append(result.location.street.name).append(", ")
//                append(result.location.city).append(", ")
//                append(result.location.state).append(", ")
//                append(result.location.country)
//            }
//
//            // Load profile image with Glide
//            Glide.with(binding.imageViewProfile.context)
//                .load(result.picture.large)
//                .apply(RequestOptions.circleCropTransform())
//                .into(binding.imageViewProfile)
//
//            // Set click listener for item view
//            itemView.setOnClickListener {
//                val context = itemView.context
//                val intent = Intent(context, RandomDetailActivity::class.java).apply {
//                    putExtra("user", result)
//                }
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
//        val binding = RandomUserItemLayoutBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return DataViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
//        val result = getItem(position) // Use PagingDataAdapter's getItem method
//        if (result != null) {
//            holder.bind(result)
//        }
//    }
//
//    // This method is no longer needed since PagingDataAdapter handles data submission
//    // Remove updateData and clearData
//}
//
//// DiffUtil.Callback implementation
//class UserDiffCallback : DiffUtil.ItemCallback<Result>() {
//    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
//        return oldItem.id == newItem.id // Assuming 'id' is a unique identifier
//    }
//
//    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
//        return oldItem == newItem
//    }
//}

//class RandomUserAdapter : PagingDataAdapter<Result, RecyclerView.ViewHolder>(UserDiffCallback()) {
//
//    private var isLoading = false
//
//    companion object {
//        private const val VIEW_TYPE_USER = 0
//        private const val VIEW_TYPE_LOADING = 1
//    }
//
//    // ViewHolder for user item
//    class UserViewHolder(private val binding: RandomUserItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(result: Result) {
//            binding.textViewName.text = "${result.name.title} ${result.name.first} ${result.name.last}"
//            binding.textViewAddress.text = buildString {
//                append(result.location.street.number).append(" ")
//                append(result.location.street.name).append(", ")
//                append(result.location.city).append(", ")
//                append(result.location.state).append(", ")
//                append(result.location.country)
//            }
//
//            Glide.with(binding.imageViewProfile.context)
//                .load(result.picture.large)
//                .apply(RequestOptions.circleCropTransform())
//                .into(binding.imageViewProfile)
//
//            itemView.setOnClickListener {
//                val context = itemView.context
//                val intent = Intent(context, RandomDetailActivity::class.java).apply {
//                    putExtra("user", result)
//                }
//                context.startActivity(intent)
//            }
//        }
//    }
//
//    // ViewHolder for loading state
//    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//    override fun getItemViewType(position: Int): Int {
//        return if (isLoading && position == itemCount - 1) {
//            VIEW_TYPE_LOADING // Show loading state at the end of the list
//        } else {
//            VIEW_TYPE_USER // Show user item
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            VIEW_TYPE_LOADING -> LoadingViewHolder(
//                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
//            )
//            else -> UserViewHolder(
//                RandomUserItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            )
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        when (holder) {
//            is UserViewHolder -> {
//                val user = getItem(position)
//                if (user != null) {
//                    holder.bind(user)
//                }
//            }
//            is LoadingViewHolder -> {
//                // Optional: bind loading view here if needed
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if (isLoading) super.getItemCount() + 1 else super.getItemCount() // Add one for loading view
//    }
//
//    fun setLoadingState(loading: Boolean) {
//        isLoading = loading
//        notifyItemInserted(itemCount) // Notify that a new item (loading) has been inserted
//    }
//}
//
//// DiffUtil.ItemCallback implementation
//class UserDiffCallback : DiffUtil.ItemCallback<Result>() {
//    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
//        return oldItem.id == newItem.id // Assuming 'id' is a unique identifier
//    }
//
//    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
//        return oldItem == newItem
//    }
//}


class RandomUserPagingAdapter :
    PagingDataAdapter<Result, RandomUserPagingAdapter.DataViewHolder>(DiffCallback()) {

    class DataViewHolder(private val binding: RandomUserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            val name = "${result.name.title} ${result.name.first} ${result.name.last}"
            binding.textViewName.text = name

            val address = "${result.location.street.number} ${result.location.street.name}, " +
                    "${result.location.city}, ${result.location.state}, ${result.location.country}"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = RandomUserItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id // Assuming Result has a unique identifier
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}
