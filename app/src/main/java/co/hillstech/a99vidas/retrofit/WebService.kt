package co.hillstech.a99vidas.retrofit

import co.hillstech.a99vidas.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WebService {
    companion object {
        fun create(): ServiceInterface {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.HTTP_SERVER)
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .build().create(ServiceInterface::class.java)
        }
    }

}