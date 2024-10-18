package me.praveenpayasi.randomuserapp.ui.random

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.RandomUserApplication
import me.praveenpayasi.randomuserapp.databinding.ActivityRandomUserBinding
import me.praveenpayasi.randomuserapp.di.component.DaggerActivityComponent
import me.praveenpayasi.randomuserapp.di.module.ActivityModule
import javax.inject.Inject

//class RandomUserActivity : AppCompatActivity() {
//
//    @Inject
//    lateinit var newsListViewModel: RandomUserViewModel
//
//    @Inject
//    lateinit var adapter: RandomUserAdapter
//
//    private lateinit var binding: ActivityRandomUserBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        injectDependencies()
//        super.onCreate(savedInstanceState)
//        binding = ActivityRandomUserBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setupUI()
//        setupObserver()
//
//    }
//
//    private fun setupUI() {
//
//        binding.inputEdit.apply {
//            addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    newsListViewModel.onInputChanged(s.toString())
//                    adapter.clearData()
//                }
//
//                override fun afterTextChanged(s: Editable?) {}
//            })
//
//            setOnEditorActionListener { _, actionId, _ ->
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    hideKeyboard()
//                    if (newsListViewModel.inputValidationState.value) {
//                        val number = text.toString().toInt()
//                        newsListViewModel.fetchUsers(number)
//                    } else {
//                        Toast.makeText(
//                            this@RandomUserActivity,
//                            "Invalid input! Please enter a number between 1 and 5000.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//                true
//            }
//        }
//
//        val recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        recyclerView.adapter = adapter
//    }
//
//    private fun setupObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                newsListViewModel.uiState.collect {
//                    when (it) {
//                        is UiState.Success -> {
//                            binding.progressBar.visibility = View.GONE
//                            adapter.updateData(it.data)
//                            Log.d("Success", it.data.toString())
//                            binding.recyclerView.visibility = View.VISIBLE
//                        }
//
//                        is UiState.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                            binding.recyclerView.visibility = View.GONE
//                        }
//
//                        is UiState.Error -> {
//                            //Handle Error
//                            binding.progressBar.visibility = View.GONE
//                            Log.e("Error", it.message)
//                            Toast.makeText(this@RandomUserActivity, it.message, Toast.LENGTH_LONG)
//                                .show()
//                        }
//
//                        is UiState.Idle -> {
//                            // nothing
//                        }
//
//                        is UiState.Empty -> {
//                            // nothing
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun injectDependencies() {
//        DaggerActivityComponent.builder()
//            .applicationComponent((application as RandomUserApplication).applicationComponent)
//            .activityModule(ActivityModule(this)).build().inject(this)
//    }
//
//    private fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.inputEdit.windowToken, 0)
//    }
//
//}
//class RandomUserActivity : AppCompatActivity() {
//
//    @Inject
//    lateinit var newsListViewModel: RandomUserViewModel
//
//    @Inject
//    lateinit var adapter: RandomUserAdapter
//
//    private lateinit var binding: ActivityRandomUserBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        injectDependencies()
//        super.onCreate(savedInstanceState)
//        binding = ActivityRandomUserBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setupUI()
//        setupObserver()
//    }
//
//    private fun setupUI() {
//        binding.inputEdit.apply {
//            addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    newsListViewModel.onInputChanged(s.toString())
//                    adapter.submitData(lifecycle, PagingData.empty()) // Clear data on input change
//                }
//
//                override fun afterTextChanged(s: Editable?) {}
//            })
//
//            setOnEditorActionListener { _, actionId, _ ->
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    hideKeyboard()
//                    if (newsListViewModel.inputValidationState.value) {
//                        val number = text.toString().toInt()
//                        newsListViewModel.fetchUsers(number) // Trigger the data fetch
//                    } else {
//                        Toast.makeText(
//                            this@RandomUserActivity,
//                            "Invalid input! Please enter a number between 1 and 5000.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//                true
//            }
//        }
//
//        val recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                (recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
////        recyclerView.adapter = adapter.withLoadStateFooter(
////            footer = LoadStateAdapter { adapter.retry() } // Add a retry footer for paging
////        )
//        recyclerView.adapter = adapter
//    }
//
////    private fun setupObserver() {
////        lifecycleScope.launch {
////            repeatOnLifecycle(Lifecycle.State.STARTED) {
////                // Collect the PagingData flow
////                newsListViewModel.paginatedDataFlow.collectLatest { pagingData ->
////                    adapter.submitData(pagingData) // Submit the paging data to adapter
////                }
////            }
////        }
////
////        // Observe the load state of PagingDataAdapter to handle UI updates
////        adapter.addLoadStateListener { loadState ->
////            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
////            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
////            if (loadState.source.refresh is LoadState.Error) {
////                Toast.makeText(this, "Error occurred: ${(loadState.source.refresh as LoadState.Error).error.message}", Toast.LENGTH_SHORT).show()
////            }
////        }
////    }
//
//    private fun setupObserver() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                // Collect the PagingData flow
//                newsListViewModel.paginatedDataFlow.collectLatest { pagingData ->
//                    adapter.submitData(pagingData) // Submit the paging data to adapter
//                    adapter.setLoadingState(false) // Ensure loading state is cleared
//                }
//            }
//        }
//
//        // Observe the load state of PagingDataAdapter to handle UI updates
//        newsListViewModel.loadStateFlow.collectLatest { loadState ->
//            adapter.setLoadingState(loadState.source.refresh is LoadState.Loading)
//            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
//            if (loadState.source.refresh is LoadState.Error) {
//                Toast.makeText(this, "Error occurred: ${(loadState.source.refresh as LoadState.Error).error.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun injectDependencies() {
//        DaggerActivityComponent.builder()
//            .applicationComponent((application as RandomUserApplication).applicationComponent)
//            .activityModule(ActivityModule(this)).build().inject(this)
//    }
//
//    private fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.inputEdit.windowToken, 0)
//    }
//
//}

class RandomUserActivity : AppCompatActivity() {

    @Inject
    lateinit var randomUserViewModel: RandomUserViewModel

    private lateinit var binding: ActivityRandomUserBinding
    private lateinit var adapter: RandomUserPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityRandomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        adapter = RandomUserPagingAdapter()

        binding.inputEdit.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    randomUserViewModel.onInputChanged(s.toString())
                    adapter.submitData(
                        lifecycle,
                        PagingData.empty()
                    ) // Clear adapter when input is invalid
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                    if (randomUserViewModel.inputValidationState.value) {
                        val number = text.toString().toInt()
                        randomUserViewModel.fetchUsers(number) // Trigger fetch from ViewModel
                    } else {
                        Toast.makeText(
                            this@RandomUserActivity,
                            "Invalid input! Please enter a number between 1 and 5000.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                true
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { adapter.retry() },
            footer = PagingLoadStateAdapter { adapter.retry() }
        )
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                randomUserViewModel.uiState.collectLatest { pagingData ->
                    adapter.submitData(lifecycle, pagingData) // Update adapter with paginated data
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as RandomUserApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.inputEdit.windowToken, 0)
    }
}
