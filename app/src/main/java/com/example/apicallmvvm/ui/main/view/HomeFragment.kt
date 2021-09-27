package com.example.apicallmvvm.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apicallmvvm.data.api.ApiHelper
import com.example.apicallmvvm.data.api.ApiServiceImpl
import com.example.apicallmvvm.data.model.Shop
import com.example.apicallmvvm.databinding.FragmentHomeBinding
import com.example.apicallmvvm.ui.base.ViewModelFactory
import com.example.apicallmvvm.ui.main.adapter.MainAdapter
import com.example.apicallmvvm.ui.main.viewmodel.MainViewModel
import com.example.apicallmvvm.utils.Status
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.apicallmvvm.data.api.AuthInterceptor
import com.example.apicallmvvm.data.api.ShopService
import com.example.apicallmvvm.data.model.AuthToken
import com.example.apicallmvvm.data.model.ShopData
import com.example.apicallmvvm.utils.Constants
import com.example.apicallmvvm.utils.Constants.CLIENT_ID
import com.example.apicallmvvm.utils.Constants.CLIENT_SECRET
import com.example.apicallmvvm.utils.Constants.GRANT_TYPE
import com.example.apicallmvvm.utils.Constants.SCOPE
import com.example.apicallmvvm.utils.StoreToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import kotlin.collections.ArrayList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor




class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    lateinit var authInterceptor: AuthInterceptor

    private lateinit var retrofitBuilder: ShopService
    private lateinit var lemondoArrayList: ArrayList<Shop>
    private lateinit var lemondoRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        getToken()
        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun getToken() {
        authInterceptor = AuthInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor).build()
            )
            .build()
            .create(ShopService::class.java)

        val tokenData = retrofitBuilder.getToken(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, SCOPE)
        tokenData.enqueue(object : Callback<AuthToken> {
            override fun onResponse(call: Call<AuthToken>, response: Response<AuthToken>) {
                if (response.isSuccessful) {
                    val token = response.body()?.access_token!!
                    StoreToken.saveToken(token)
                    getShop()
                }
            }

            override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                Log.e("error", "${t.message}")
            }
        })
    }

    fun getShop() {
        val retrofitData = retrofitBuilder.getShopList()

        binding.progressBarId.isVisible = true

        retrofitData.enqueue(object : Callback<ShopData> {

            override fun onResponse(call: Call<ShopData>?, response: retrofit2.Response<ShopData>?) {
                val responseBody = response?.body()?.shops

                adapter = MainAdapter(responseBody!! as ArrayList<Shop>)
                adapter.notifyDataSetChanged()
                lemondoRecyclerView.adapter = adapter

                binding.progressBarId.isVisible = false

            }

            override fun onFailure(call: Call<ShopData>?, t: Throwable) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                binding.progressBarId.isVisible = false
            }
        })
    }

    private fun setupUI() {
        binding.recyclerViewId.layoutManager = LinearLayoutManager(activity)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerViewId.addItemDecoration(
            DividerItemDecoration(binding.recyclerViewId.context,
                (binding.recyclerViewId.layoutManager as LinearLayoutManager).orientation)
        )
        binding.recyclerViewId.adapter = adapter
    }

    private fun setupObserver() {
        activity?.let {
            mainViewModel.getShops().observe(it, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBarId.visibility = View.GONE
                        it.data?.let { users -> renderList(users) }
                        binding.recyclerViewId.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        binding.progressBarId.visibility = View.VISIBLE
                        binding.recyclerViewId.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        //Handle Error
                        binding.progressBarId.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    private fun renderList(shops: List<Shop>) {
        adapter.addData(shops)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}