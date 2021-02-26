package com.example.dependency_injection_with_koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {
    private val userViewModel by viewModel<UserViewModel>()
    private lateinit var userList: List<GithubUser>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userViewModel.data.observe(this, Observer {
            for (i in 0..it.size-1) {
                Log.d(TAG, "data: " + it.get(i))
            }
        })

        userViewModel.loadingState.observe(this, Observer {
            Log.d(TAG, "loadingState:" + it.status)
        })

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}