package com.example.mobileacademichub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileacademichub.data.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        // Simulate fetching initial chat messages
        viewModelScope.launch {
            _messages.value = listOf(
                Message("1", "Dosen A", "Selamat pagi, ada yang ingin ditanyakan tentang materi minggu lalu?", System.currentTimeMillis() - 60000),
                Message("2", "Mahasiswa B", "Pagi, Pak. Saya ingin bertanya tentang implementasi algoritma X.", System.currentTimeMillis() - 30000),
                Message("3", "Dosen A", "Baik, silakan sampaikan pertanyaan Anda.", System.currentTimeMillis() - 10000)
            )
        }
    }

    fun sendMessage(sender: String, content: String) {
        viewModelScope.launch {
            val newMessage = Message(
                id = (_messages.value.size + 1).toString(),
                sender = sender,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            _messages.value = _messages.value + newMessage
        }
    }
}
