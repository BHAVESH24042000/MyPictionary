package com.example.mypictionary.repository

import android.content.Context
import com.example.mypictionary.R
import com.example.mypictionary.data.Room
import com.example.mypictionary.data.response.BasicApiResponse
import com.example.mypictionary.remote.api.MyPictionaryApiService
import com.example.mypictionary.util.ApiResultWrapper
import com.example.mypictionary.util.Keys
import com.example.mypictionary.util.checkForInternetConnection
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class DefaultSetupRepository @Inject constructor(
    private val myPictionaryApiService: MyPictionaryApiService,
    private val context: Context
) : SetupRepository {
    override suspend fun createRoom(room: Room): ApiResultWrapper<BasicApiResponse> {
        if (!context.checkForInternetConnection()) {
            return ApiResultWrapper.Error(
                context.getString(R.string.error_internet_turned_off),
                null
            )
        }

        val response = try {
            myPictionaryApiService.createRoom(room)
        } catch (e: Exception) {
            return ApiResultWrapper.Error(context.getString(R.string.error_http), null)
        }

        if (response.isSuccessful && response.body()?.successful == true) {
            return ApiResultWrapper.Success(null)
        } else if (response.body()?.successful == false) {
            return ApiResultWrapper.Error(response.body()?.message, null)
        } else {
            return ApiResultWrapper.Error(
                context.getString(R.string.check_internet_connection),
                null
            )
        }
    }

    override suspend fun getRooms(searchQuery: String): ApiResultWrapper<List<Room>> {
        if (!context.checkForInternetConnection()) {
            return ApiResultWrapper.Error(
                context.getString(R.string.error_internet_turned_off),
                null
            )
        }

        val response = try {
            myPictionaryApiService.getRooms(searchQuery)
        } catch (e: Exception) {
            return ApiResultWrapper.Error(context.getString(R.string.error_http), null)
        }

        if (response.isSuccessful && (!response.body().isNullOrEmpty())) {
            return ApiResultWrapper.Success(null)
        } else {
            return ApiResultWrapper.Error(
                context.getString(R.string.check_internet_connection),
                null
            )
        }
    }

    override suspend fun joinRoom(
        username: String,
        roomName: String
    ): ApiResultWrapper<BasicApiResponse> {
        if (!context.checkForInternetConnection()) {
            return ApiResultWrapper.Error(
                context.getString(R.string.error_internet_turned_off),
                null
            )
        }

        val response = try {
            myPictionaryApiService.joinRoom(username, roomName)
        } catch (e: Exception) {
            return ApiResultWrapper.Error(context.getString(R.string.error_http), null)
        }

        if (response.isSuccessful && response.body()?.successful == true) {
            return ApiResultWrapper.Success(null)
        } else if (response.body()?.successful == false) {
            return ApiResultWrapper.Error(response.body()?.message, null)
        } else {
            return ApiResultWrapper.Error(
                context.getString(R.string.check_internet_connection),
                null
            )
        }
    }
}