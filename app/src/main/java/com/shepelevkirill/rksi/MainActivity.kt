package com.shepelevkirill.rksi

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.shepelevkirill.rksi.model.core.models.ScheduleModel
import com.shepelevkirill.rksi.model.core.models.SubjectModel
import com.shepelevkirill.rksi.model.impl.deserializers.LocalDateDeserializer
import com.shepelevkirill.rksi.model.impl.deserializers.LocalTimeDeserializer
import com.shepelevkirill.rksi.model.impl.network.Api
import com.shepelevkirill.rksi.model.impl.repository.ScheduleRepositoryImpl
import com.shepelevkirill.rksi.view.adapters.ScheduleAdapter
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.Subject

class MainActivity : AppCompatActivity() {

    val disposer = CompositeDisposable()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onDestroy() {
        super.onDestroy()
        disposer.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ScheduleAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer())
        gsonBuilder.registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://rksi.ru/export/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(client)
            .build()

        val repository = ScheduleRepositoryImpl(retrofit.create(Api::class.java))

        val req = repository.getScheduleForGroup("ПОКС-11").subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                it.forEach {
                    adapter.add(it.date)
                    it.schedule.forEach {
                        adapter.add(it)
                    }
                }
            }) {
                Log.w("ERROR", it.toString())
            }
        disposer.add(req)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
