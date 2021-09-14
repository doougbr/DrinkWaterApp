package com.example.bebaagua.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bebaagua.NewsAdapter
import com.example.bebaagua.R
import com.example.bebaagua.api.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class NewsFragment : Fragment() {

    companion object {
        const val TAG = "NewsFragment"
    }

    var name: String? = null
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var textView = view.findViewById<TextView>(R.id.text_view_header)
        var progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        loadSharedPreferences()
        setupRecyclerView()

        textView.text = "Olá, $name"

        lifecycleScope.launchWhenCreated {
            progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getNews()
            } catch(e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection.")
                return@launchWhenCreated
            } catch(e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response.")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                newsAdapter.news = response.body()!!.articles!!
            } else {
                Log.e(TAG, "Response not successful")
                Log.e(TAG, "Is response successful? ${response.isSuccessful}")
                Log.e(TAG, "Is response null? ${response.body().toString()}")
            }
            progressBar.isVisible = false
        }
    }

    private fun loadSharedPreferences() {
        val sharedPreferences = this.activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        name = sharedPreferences?.getString("name", "")
    }

    private fun setupRecyclerView() = view?.findViewById<RecyclerView>(R.id.recycler_view_news)
        ?.apply {
        newsAdapter = NewsAdapter()
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(context)
    }
}