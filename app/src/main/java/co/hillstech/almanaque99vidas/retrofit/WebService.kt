package co.hillstech.almanaque99vidas.retrofit

import co.hillstech.almanaque99vidas.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    companion object {
        fun create(): ServiceInterface {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.HTTP_SERVER)
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .build().create(ServiceInterface::class.java)
        }
    }

}