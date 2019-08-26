package com.past3.ketroapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.main_activity.*

/*class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private val listAdapter by lazy {
        MyListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            it.adapter = listAdapter
            it.setHasFixedSize(true)
        }

        listAdapter.submitList(viewModel.list)
        searchButton.setOnClickListener(this@MainActivity)

        viewModel._liveData.observe(this, object : Kobserver<ResponseModel>() {
            override fun onSuccess(data: ResponseModel) {
                Toast.makeText(this@MainActivity, "Works", Toast.LENGTH_LONG).show()
            }

            override fun onException(exception: Exception) {
                userErrorHanlder(exception)
            }
        })

    }

    private fun userErrorHanlder(ex: Exception) {
        when (ex) {
            is GitHubErrorHandler.ErrorConfig.NetworkException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            is GitHubErrorHandler.ErrorConfig.GitHubException -> {
                Toast.makeText(this@MainActivity, ex.message, Toast.LENGTH_LONG).show()
            }
            else -> Toast.makeText(this@MainActivity, "Oops! Something went wrong.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View?) {
        recyclerView.visibility = View.GONE
        errorView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        viewModel.getGitHubUser()
        /*viewModel.searchUser(editText.text.toString()).observe(this, object : Kobserver<ResponseModel>() {
            override fun onSuccess(data: ResponseModel) {
                if (data.items.isEmpty()) {
                    toggleViews(false)
                    return
                }
                toggleViews(true)
                viewModel.list.let {
                    it.clear()
                    it.addAll(data.items)
                    listAdapter.submitList(it)
                }
            }

        })*/
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
*/