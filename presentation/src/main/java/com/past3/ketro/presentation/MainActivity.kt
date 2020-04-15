package com.past3.ketro.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.past3.ketro.api.Kobserver
import com.past3.ketro.domain.entities.ErrorConfig
import com.past3.ketro.domain.entities.Items
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private val listAdapter by lazy {
        MyListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            it.adapter = listAdapter
            it.setHasFixedSize(true)
        }

        listAdapter.submitList(viewModel.list)
        searchButton.setOnClickListener(this@MainActivity)

        viewModel._liveData.observe(this, Observer {
            toggleViews(true)
            Toast.makeText(this@MainActivity, "Works", Toast.LENGTH_LONG).show()
        })

        viewModel.errorLiveData.observe(this, Observer { ex ->
            ex?.let {
                userErrorHanlder(it)
            }
        })

    }

    private fun userErrorHanlder(ex: Exception) {
        toggleViews(false)
        when (ex) {
            is ErrorConfig.NetworkException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            is ErrorConfig.GitHubException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this@MainActivity, "Oops! Something went wrong.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        recyclerView.visibility = View.GONE
        errorView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.searchUser(editText.text.toString())
    }

    fun toggleViews(dataAvailable: Boolean) {
        progressBar.visibility = View.GONE
        if (dataAvailable) {
            errorView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.GONE
            errorView.visibility = View.VISIBLE
        }
    }

}