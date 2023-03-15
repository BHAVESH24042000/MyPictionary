package com.example.mypictionary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypictionary.data.Room
import com.example.mypictionary.repository.SetupRepository
import com.example.mypictionary.util.ApiResultWrapper
import com.example.mypictionary.util.Keys
import com.example.mypictionary.util.Keys.MAX_ROOM_NAME_LENGTH
import com.example.mypictionary.util.Keys.MAX_USERNAME_LENGTH
import com.example.mypictionary.util.Keys.MIN_ROOM_NAME_LENGTH
import com.example.mypictionary.util.Keys.MIN_USERNAME_LENGTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetupViewModel @Inject constructor(
    private val setupRepository: SetupRepository
) : ViewModel() {

    private val _setupEvent = MutableSharedFlow<SetupEvent>()
    val setupEvent: SharedFlow<SetupEvent> = _setupEvent

    private val _rooms = MutableStateFlow<SetupEvent>(SetupEvent.GetRoomEmptyEvent)
    val rooms: StateFlow<SetupEvent> = _rooms

    fun validateUsernameAndNavigateToSelectRoom(username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val trimmedUsername = username.trim()
            when {
                trimmedUsername.isEmpty() -> {
                    _setupEvent.emit(SetupEvent.InputEmptyError)
                }
                trimmedUsername.length < MIN_USERNAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooShortError)
                }
                trimmedUsername.length > MAX_USERNAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooLongError)
                }
                else -> _setupEvent.emit(SetupEvent.NavigateToSelectRoomEvent(username))
            }
        }
    }

    fun createRoom(room: Room) {
        viewModelScope.launch(Dispatchers.Main) {
            val trimmedRoomName = room.name.trim()
            when {
                trimmedRoomName.isEmpty() -> {
                    _setupEvent.emit(SetupEvent.InputEmptyError)
                }
                trimmedRoomName.length < MIN_ROOM_NAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooShortError)
                }
                trimmedRoomName.length > MAX_ROOM_NAME_LENGTH -> {
                    _setupEvent.emit(SetupEvent.InputTooLongError)
                }
                else -> {
                    val result = setupRepository.createRoom(room)
                    when (result) {
                        is ApiResultWrapper.Success -> {
                            _setupEvent.emit(SetupEvent.CreateRoomEvent(room))
                        }
                        else -> {
                           _setupEvent.emit(SetupEvent.CreateRoomErrorEvent(result.message?: Keys.GENERAL_ERROR_MSG))
                        }
                    }

                }
            }
        }
    }

    fun getRooms(searchQuery: String) {
        _rooms.value = SetupEvent.GetRoomLoadingEvent
        viewModelScope.launch(Dispatchers.Main) {
            val result = setupRepository.getRooms(searchQuery)
            if(result is ApiResultWrapper.Success) {
                _rooms.value = SetupEvent.GetRoomEvent(result.data ?: return@launch)
            } else {
                _setupEvent.emit(SetupEvent.GetRoomErrorEvent(result.message ?: return@launch))
            }
        }
    }

    fun joinRoom(username: String, roomName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = setupRepository.joinRoom(username, roomName)
            if(result is ApiResultWrapper.Success) {
                _setupEvent.emit(SetupEvent.JoinRoomEvent(roomName))
            } else {
                _setupEvent.emit(SetupEvent.JoinRoomErrorEvent(result.message ?: return@launch))
            }
        }
    }

    // things are in dispatchers.main as flows handling should be done in main thread.
    //and retrofit designed in such way that it handles automatically network calls in IO mode automatically.


}

sealed class SetupEvent {
    object InputEmptyError : SetupEvent()
    object InputTooShortError : SetupEvent()
    object InputTooLongError : SetupEvent()
    object GetRoomLoadingError : SetupEvent()
    object GetRoomLoadingEvent : SetupEvent()
    object GetRoomEmptyEvent : SetupEvent()
    data class CreateRoomEvent(val room: Room) : SetupEvent()
    data class CreateRoomErrorEvent(val message: String) : SetupEvent()
    data class NavigateToSelectRoomEvent(val username: String) : SetupEvent()
    data class NavigateToSelectRoomErrorEvent(val error: String) : SetupEvent()
    data class GetRoomEvent(val rooms: List<Room>) : SetupEvent()
    data class GetRoomErrorEvent(val error: String) : SetupEvent()
    data class JoinRoomEvent(val roomName: String) : SetupEvent()
    data class JoinRoomErrorEvent(val error: String) : SetupEvent()
}