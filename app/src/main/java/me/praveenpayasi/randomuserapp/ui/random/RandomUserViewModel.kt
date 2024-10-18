package me.praveenpayasi.randomuserapp.ui.random

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
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

    val paginatedDataFlow: Flow<PagingData<Result>> = emptyFlow()


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
        viewModelScope.launch {
            randomUserRepository.getRandomUsers(count)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _uiState.value = it
                }
        }
    }

}

//class RandomUserViewModel(private val randomUserRepository: RandomUserRepository) : ViewModel() {
//
//    private val _uiState = MutableStateFlow<PagingData<Result>>(value = PagingData.empty())
//    val uiState: StateFlow<PagingData<Result>> = _uiState
//
//    private val _inputValidationState = MutableStateFlow(false)
//    val inputValidationState: StateFlow<Boolean> = _inputValidationState
//
//    private val _inputText = MutableStateFlow("")
//    val inputText: StateFlow<String> = _inputText
//
//    // Added load state to manage loading states
//    private val _loadState = MutableStateFlow<LoadState>(LoadState.Loading)
//    val loadState: StateFlow<LoadState> = _loadState
//
//    private var currentPagingData: Flow<PagingData<Result>> = emptyFlow()
//
//    private fun validateInput(input: String) {
//        val isValid = input.isNotEmpty() && input.isDigitsOnly() && input.toIntOrNull()?.let {
//            it in 1..5000
//        } ?: false
//        _inputValidationState.value = isValid
//    }
//
//    fun onInputChanged(input: String) {
//        _inputText.value = input
//        validateInput(input)
//        if (!inputValidationState.value) {
//            _uiState.value = PagingData.empty()
//        }
//    }
//
//    fun fetchUsers(count: Int) {
//        viewModelScope.launch {
//            randomUserRepository.getRandomUsers(count)
//                .cachedIn(viewModelScope)
//                .collectLatest { pagingData ->
//                    _uiState.value = pagingData
//                    // Update load state based on the current PagingData
//                    _loadState.value = if (pagingData.itemCount > 0) {
//                        LoadState.NotLoading
//                    } else {
//                        LoadState.Error("No data found.")
//                    }
//                }
//        }
//    }
//
//    // Optional: You can create a function to reset the load state
//    fun resetLoadState() {
//        _loadState.value = LoadState.Loading
//    }
//}
//
//// Sealed class to represent the LoadState
//sealed class LoadState {
//    object Loading : LoadState()
//    object NotLoading : LoadState()
//    data class Error(val message: String) : LoadState()
//}