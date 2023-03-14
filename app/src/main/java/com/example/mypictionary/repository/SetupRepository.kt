package com.example.mypictionary.repository

import com.example.mypictionary.data.Room
import com.example.mypictionary.data.response.BasicApiResponse
import com.example.mypictionary.util.ApiResultWrapper

interface SetupRepository {
    suspend fun createRoom(room : Room) : ApiResultWrapper<BasicApiResponse>
    suspend fun getRooms(searchQuery: String): ApiResultWrapper<List<Room>>
    suspend fun joinRoom(username: String, roomName: String): ApiResultWrapper<BasicApiResponse>
}