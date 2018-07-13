package com.past3.ketro


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.past3.ketro.api.Kobserver
import kotlinx.android.synthetic.main.fragment_main.*
import test.smile.lobby.adapter.SearchAdapter
import test.smile.lobby.adapter.VehicleAdapter
import test.smile.lobby.model.GenericVehicleContainer
import test.smile.lobby.repo.LobbyErrorHandler
import test.smile.lobby.repo.viewmodel.LobbyViewModel
import test.smile.lobby.utils.AutoFitGridlayout
import test.smile.lobby.utils.EndlessRecyclerViewScrollListener


abstract class BaseLobbyFragment : Fragment(), VehicleAdapter.Companion.VehicleClickListener {

    lateinit var viewModel: LobbyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LobbyViewModel::class.java)
        val vehicleAdapter = VehicleAdapter(viewModel.genericList, false, this)
        val layoutAutoManager = AutoFitGridlayout(context!!, 750)
        recyclerView.apply {
            layoutManager = layoutAutoManager
            adapter = vehicleAdapter
            setHasFixedSize(true)
        }.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutAutoManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (!viewModel.isLast) {
                    apiQuery(page)
                }
            }
        })
        initVariables()
        val searchAdapter = SearchAdapter(context!!, R.layout.auto_text, viewModel.genericList as ArrayList)
        searchView.threshold = 3
        searchView.setOnItemClickListener { parent, view, position, id ->
            onClick(viewModel.genericList[position])
        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            viewModel.genericList.clear()
            apiQuery(0)
        }
        swipeRefresh.isRefreshing = true

        viewModel.responseData().observe(this, object : Kobserver<List<GenericVehicleContainer>>() {
            override fun onException(exception: Exception) {
                if (exception is LobbyErrorHandler.ErrorConfig.UpdateException) {
                    //handle error show dialog or redirect user/etc...
                }
            }

            override fun onSuccess(data: List<GenericVehicleContainer>) {
                swipeRefresh.isRefreshing = false
                searchView.visibility = View.VISIBLE
                helperContainer.visibility = View.VISIBLE

                viewModel.genericList.addAll(data)
                vehicleAdapter.notifyDataSetChanged()
                searchView.setAdapter(searchAdapter)
                searchAdapter.notifyDataSetChanged()

            }
        })

        if (viewModel.genericList.isEmpty()) {
            apiQuery(0)
        }
        helperText.text = Html.fromHtml(helperTextDesc())

    }

    abstract fun apiQuery(page: Int)

    abstract fun onSelectionAction(data: GenericVehicleContainer)

    abstract fun initVariables()

    abstract fun helperTextDesc(): String

    override fun onClick(data: GenericVehicleContainer) {
        onSelectionAction(data)
    }
}
