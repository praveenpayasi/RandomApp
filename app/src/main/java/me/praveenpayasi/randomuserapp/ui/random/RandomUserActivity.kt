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
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.RandomUserApplication
import me.praveenpayasi.randomuserapp.databinding.ActivityRandomUserBinding
import me.praveenpayasi.randomuserapp.di.component.DaggerActivityComponent
import me.praveenpayasi.randomuserapp.di.module.ActivityModule
import javax.inject.Inject

class RandomUserActivity : AppCompatActivity() {

    @Inject
    lateinit var randomUserViewModel: RandomUserViewModel

    private lateinit var binding: ActivityRandomUserBinding

    @Inject
    lateinit var randomAdapter: RandomUserPaginationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityRandomUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = randomAdapter
        }

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
                    randomAdapter.submitData(lifecycle, PagingData.empty())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard()
                    if (randomUserViewModel.inputValidationState.value) {
                        val number = text.toString().toInt()
                        randomUserViewModel.fetchUsers(number)
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
    }
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                randomUserViewModel.uiState.collectLatest { pagingData ->
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    randomAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as RandomUserApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build().inject(this)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.inputEdit.windowToken, 0)
    }

}

