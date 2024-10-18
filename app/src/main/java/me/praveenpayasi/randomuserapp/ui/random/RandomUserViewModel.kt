package me.praveenpayasi.randomuserapp.ui.random

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.utils.DispatcherProvider
import me.praveenpayasi.randomuserapp.utils.NetworkHelper
import me.praveenpayasi.randomuserapp.utils.logger.Logger

class RandomUserViewModel(
    private val randomUserRepository: RandomUserRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider,
    private val logger: Logger
) : ViewModel() {

    private val _uiState = MutableStateFlow<PagingData<Result>>(value = PagingData.empty())

    val uiState: StateFlow<PagingData<Result>> = _uiState

    private val _inputValidationState = MutableStateFlow(false)
    val inputValidationState: StateFlow<Boolean> = _inputValidationState

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

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
        if (checkInternetConnection()) {
            viewModelScope.launch(dispatcherProvider.main) {
                randomUserRepository.getRandomUsers(count)
                    .flowOn(dispatcherProvider.io)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _uiState.value = it
                    }
            }
        } else {
            logger.d("No Internet", "Pls connect to internet")
        }
    }
}