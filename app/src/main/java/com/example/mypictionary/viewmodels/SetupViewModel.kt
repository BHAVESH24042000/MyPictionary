package com.example.mypictionary.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mypictionary.data.Room
import com.example.mypictionary.repository.SetupRepository
import javax.inject.Inject

class SetupViewModel @Inject constructor(
    private val setupRepository: SetupRepository
) : ViewModel() {

    sealed class SetupEvent {
        object InputEmptyError : SetupEvent()
        object InputTooShortError : SetupEvent()
        object InputTooLongError : SetupEvent()
        object GetRoomLoadingError : SetupEvent()
        object GetRoomEmptyEvent : SetupEvent()
        data class CreateRoomEvent(val room: Room): SetupEvent()
        data class CreateRoomErrorEvent(val room: Room): SetupEvent()
        data class NavigateToSelectRoomEvent(val username: String): SetupEvent()
        data class NavigateToSelectRoomErrorEvent(val error: String): SetupEvent()
        data class GetRoomEvent(val rooms: List<Room>): SetupEvent()
        data class GetRoomErrorEvent(val error: String): SetupEvent()
        data class JoinRoomEvent(val roomName: String): SetupEvent()
        data class JoinRoomErrorEvent(val error: String): SetupEvent()
    }



}