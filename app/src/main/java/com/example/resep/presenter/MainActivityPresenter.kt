package com.example.resep.presenter

import com.example.resep.contract.MainActivityContract
import com.example.resep.models.Post
import com.example.resep.responses.WrappedListResponse
import com.example.resep.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(v : MainActivityContract.MainActivityView?) : MainActivityContract.MainActivityPresenter {
    private var view : MainActivityContract.MainActivityView? = v
    private var apiService  = APIClient.APIService()
    override fun all(token: String) {
        val request = apiService.all(token)
        request.enqueue(object : Callback<WrappedListResponse<Post>>{
            override fun onFailure(call: Call<WrappedListResponse<Post>>, t: Throwable) {
                println("LOG : ${t.message}")
                view?.showToast("Cannot Connect to server")
            }

            override fun onResponse(call: Call<WrappedListResponse<Post>>, response: Response<WrappedListResponse<Post>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null && body.status.equals("1")){
                        view?.attachToRecycler(body.data)
                    }
                }else{
                    view?.showToast("Something wrong, try again later")
                }
            }
        })
    }

    override fun destroy() {
        view = null
    }
}