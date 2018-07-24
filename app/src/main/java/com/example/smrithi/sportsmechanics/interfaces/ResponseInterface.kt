package com.example.smrithi.sportsmechanics.interfaces

import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchPlayerResponse
import retrofit2.Response

interface ResponseInterface {

    fun onSuccess(response: Response<SearchList>?, firstQuery: Boolean)
    fun onFailure(t: Throwable?, firstQuery: Boolean)

    fun onSuccess_SearchPlayer(response: Response<SearchPlayerResponse>?, field_name: String)
    fun onFailure_SearchPlayer(t: Throwable?)
}