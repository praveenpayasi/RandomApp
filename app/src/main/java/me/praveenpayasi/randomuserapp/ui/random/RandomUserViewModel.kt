package me.praveenpayasi.randomuserapp.ui.random

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.ui.base.UiState

class RandomUserViewModel(private val randomUserRepository: RandomUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Result>>>(UiState.Idle)

    val uiState: StateFlow<UiState<List<Result>>> = _uiState

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
            _uiState.value = UiState.Empty
        }
    }

    fun fetchNews(count: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            randomUserRepository.getTopHeadlines(count)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}