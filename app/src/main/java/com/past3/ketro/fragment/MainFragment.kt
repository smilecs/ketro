package com.past3.ketro.fragment

import test.smile.lobby.BaseLobbyFragment
import test.smile.lobby.R
import test.smile.lobby.adapter.VehicleAdapter
import test.smile.lobby.model.GenericVehicleContainer


class MainFragment : BaseLobbyFragment(), VehicleAdapter.Companion.VehicleClickListener {

    override fun apiQuery(page: Int) {
        viewModel.getManufacturer(page)
    }

    override fun onSelectionAction(data: GenericVehicleContainer) {
        //do something
    }

    override fun helperTextDesc(): String = getString(R.string.manu_help_text)

    override fun initVariables() {

    }
}
