package com.example.resep.presenter

import android.content.Context
import com.example.resep.contract.RegisterActivityContract
import com.example.resep.models.User
import com.example.resep.responses.WrappedResponse
import com.example.resep.utilities.APIClient
import com.example.resep.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(v : RegisterActivityContract.RegisterActivityView): RegisterActivityContract.RegisterActivityPresenter {
    private var view : RegisterActivityContract.RegisterActivityView? = v
    private var apiService = APIClient.APIService()
    override fun register(name: String, email: String, password: String, context: Context) {
        view?.showLoading()
        //    fun register(@Field("name")name : String, @Field("email")email: String, @Field("password")password: String): Call<WrappedResponse<User>>
        val request = apiService.register(name, email, password)
        request.enqueue(object :Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Cant connect to server")
                println(t.message)
                view?.hideLoading()
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body!=null && body.status.equals("1")){
                        Constants.setToken(context, body.data.api_token!!)
                        view?.showToast("Register Success")
                        view?.hideLoading()
                    }else{
                        view?.showToast("Register Failed, email might be allready used")
                    }
                    view?.hideLoading()
                }else{
                    view?.showToast("Something Wrong, Try again later")
                    view?.hideLoading()
                }
            }

        })
    }

    override fun destroy() {
        view= null
    }
}