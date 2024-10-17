package me.praveenpayasi.randomuserapp.ui.random

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.RandomUserApplication
import me.praveenpayasi.randomuserapp.databinding.ActivityRandomUserBinding
import me.praveenpayasi.randomuserapp.di.component.DaggerActivityComponent
import me.praveenpayasi.randomuserapp.di.module.ActivityModule
import me.praveenpayasi.randomuserapp.ui.base.UiState
import javax.inject.Inject

class RandomUserActivity : AppCompatActivity() {

    @Inject
    lateinit var newsListViewModel: RandomUserViewModel

    @Inject
    lateinit var adapter: RandomUserAdapter

    private lateinit var binding: ActivityRandomUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityRandomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()

    }

    private fun setupUI() {

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
                    newsListViewModel.onInputChanged(s.toString())
                    adapter.clearData()
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                    if (newsListViewModel.inputValidationState.value) {
                        val number = text.toString().toInt()
                        newsListViewModel.fetchNews(number)
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

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.updateData(it.data)
                            Log.d("Success", it.data.toString())
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            Log.e("Error", it.message)
                            Toast.makeText(this@RandomUserActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }

                        is UiState.Idle -> {
                            // nothing
                        }

                        is UiState.Empty -> {
                            // nothing
                        }
                    }
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
