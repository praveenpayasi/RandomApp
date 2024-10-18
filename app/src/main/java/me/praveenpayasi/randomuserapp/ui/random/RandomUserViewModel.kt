package me.praveenpayasi.randomuserapp.ui.random

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository

class RandomUserViewModel(private val randomUserRepository: RandomUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Result>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<Result>> = _uiState

    private val _inputValidationState = MutableStateFlow(false)
    val inputValidationState: StateFlow<Boolean> = _inputValidationState

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private fun validateInput(input: String) {
        val isValid = input.isNotEmpty() && input.isDigitsOnly() && input.toIntOrNull()?.let {
            it in 1..5000
        } ?: false
        _inputValidationState.value = isValid
    }

    fun onInputChanged(input: String) {
        _inputText.value = input
        validateInput(input)
        if (!inputValidationState.value) {
            _uiState.value = PagingData.empty()
        }
    }

    fun fetchUsers(count: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            randomUserRepository.getRandomUsers(count)
                .flowOn(Dispatchers.IO)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _uiState.value = it
                }
        }
    }

}