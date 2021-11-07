package com.damask.chucknorrisjokes

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.damask.chucknorrisjokes.databinding.FragmentJokesBinding
import com.xwray.groupie.GroupieAdapter
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.app.Activity

private const val COUNT_KEY = "COUNT_KEY"

class JokesFragment : Fragment(R.layout.fragment_jokes) {

    private val binding: FragmentJokesBinding by viewBinding()

    private var url = "https://api.icndb.com/jokes/random/"
    private var okHttpClient: OkHttpClient = OkHttpClient()

    private val adapter = GroupieAdapter()

    var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // Скрываем keyboard при скролинге
        recyclerView.addOnScrollListener(object: OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val inputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(recyclerView.windowToken, 0)
            }
        })

        binding.reloadBtn.setOnClickListener {

            // Чистим прошлое кол-во шуток
            adapter.clear()

            count = binding.countEt.text.toString().toInt()

            // Проверяем count и загружаем шутки
            when {
                count <= 0 -> Toast.makeText(context,
                    "Please, enter a number greater than 0", Toast.LENGTH_SHORT).show()
                count > 574 -> Toast.makeText(context,
                    "There are maximum 574 jokes\nPlease enter a number less or equally 574", Toast.LENGTH_LONG).show()
                count > 0 -> {
                    binding.progressBar.visibility = View.VISIBLE
                    loadRandomJoke(count)
                }
                else -> Toast.makeText(context,
                    "Please, enter a number", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun loadRandomJoke(count: Int) {
        url = "https://api.icndb.com/jokes/random/$count"

        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {}

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                var i = 0
                while(i < count) {
                    val joke = (JSONObject(json).getJSONArray("value").getJSONObject(i).get("joke")).toString()
                    val formatedJoke = "${i+1}. " + Html.fromHtml(joke).toString()
                    requireActivity().runOnUiThread {
                        binding.progressBar.visibility = View.GONE
                        adapter.add(JokeItem(formatedJoke))
                    }
                    i++
                }

            }
        })
    }
}