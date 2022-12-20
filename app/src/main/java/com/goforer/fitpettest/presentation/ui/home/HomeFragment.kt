package com.goforer.fitpettest.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goforer.base.extension.RECYCLER_VIEW_CACHE_SIZE
import com.goforer.base.extension.gone
import com.goforer.base.extension.launchAndRepeatWithViewLifecycle
import com.goforer.base.extension.show
import com.goforer.fitpettest.data.Params
import com.goforer.fitpettest.data.Query
import com.goforer.fitpettest.presentation.ui.home.adapter.CityWeatherAdapter
import com.goforer.fitpettest.data.source.model.entity.home.response.CityWeatherResponse
import com.goforer.fitpettest.data.source.model.entity.home.response.WeatherInfo
import com.goforer.fitpettest.data.source.network.response.Status
import com.goforer.fitpettest.databinding.FragmentHomeBinding
import com.goforer.fitpettest.presentation.stateholders.MediatorViewModel
import com.goforer.fitpettest.presentation.stateholders.home.GetCityWeatherViewModel
import com.goforer.fitpettest.presentation.ui.BaseFragment
import com.goforer.fitpettest.presentation.ui.MainActivity
import com.goforer.fitpettest.presentation.ui.home.adapter.CityWeatherAdapter.Companion.VIEW_CITY_NAME_VIEW
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    private val cities = listOf(SEOUL, LONDON, CHICAGO)

    private val seoulWeathers = mutableListOf<WeatherInfo>()
    private val londonWeathers = mutableListOf<WeatherInfo>()
    private val chicagoWeathers = mutableListOf<WeatherInfo>()
    private var citiesWeathers = mutableListOf<WeatherInfo>()

    private var index = 0

    @Inject
    internal lateinit var getCityWeatherViewModelFactory: GetCityWeatherViewModel.AssistedVMFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCityWeatherAdapter()
        loadWeathers(cities)
    }

    override fun onDestroyView() {
        binding.rvWeather.adapter = null

        super.onDestroyView()
    }

    private fun setCityWeatherAdapter() {
        cityWeatherAdapter = CityWeatherAdapter(mainActivity)
        setRecyclerView(mainActivity, cityWeatherAdapter)
    }

    private fun setRecyclerView(context: Context, cityWeatherAdapter: CityWeatherAdapter) {
        with(binding) {
            rvWeather.apply {
                val linearLayoutManager =
                    LinearLayoutManager(context as MainActivity, RecyclerView.VERTICAL, false)

                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                cityWeatherAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                itemAnimator?.changeDuration = 0
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                setHasFixedSize(false)
                setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
                layoutManager = linearLayoutManager
                adapter = cityWeatherAdapter
            }
        }
    }

    private fun loadWeathers(cities: List<String>) {
        cities.forEachIndexed { _, city ->
            lifecycleScope.launch {
                submitWeathers(createViewModel(city))
            }
        }
    }

    private fun createViewModel(city: String) = GetCityWeatherViewModel.provideFactory(
        getCityWeatherViewModelFactory, Params(Query(city))
    ).create(GetCityWeatherViewModel::class.java)

    private fun submitWeathers(viewModel: MediatorViewModel) {
        view?.let {
            launchAndRepeatWithViewLifecycle {
                viewModel.value.collectLatest { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                @Suppress("UNCHECKED_CAST")
                                val result = resource.getData() as? CityWeatherResponse

                                result?.let {
                                    index++
                                    when(result.city.name) {
                                        SEOUL -> {
                                            seoulWeathers.add(fillCityName(result.city.name))
                                            seoulWeathers.addAll(it.list)
                                        }
                                        LONDON -> {
                                            londonWeathers.add(fillCityName(result.city.name))
                                            londonWeathers.addAll(it.list)
                                        }
                                        CHICAGO -> {
                                            chicagoWeathers.add(fillCityName(result.city.name))
                                            chicagoWeathers.addAll(it.list)
                                        }
                                        else -> {}
                                    }

                                    if (index == cities.size)
                                        addWeathers(seoulWeathers, londonWeathers, chicagoWeathers)

                                }
                            }
                        }

                        Status.ERROR -> {
                            setLoading(false)
                        }

                        Status.LOADING -> {
                            setLoading(true)
                        }
                    }
                }
            }
        }
    }

    private fun setLoading(enabled: Boolean) {
        lifecycleScope.launch {
            with(binding) {
                if (enabled) {
                    shimmerViewContainer.show()
                    shimmerViewContainer.startShimmer()
                } else {
                    shimmerViewContainer.gone()
                    shimmerViewContainer.stopShimmer()
                }
            }
        }
    }

    private fun addWeathers(
        seoulWeathers: List<WeatherInfo>,
        londonWeathers: List<WeatherInfo>,
        chicagoWeathers: List<WeatherInfo>
    ) {
        citiesWeathers.addAll(seoulWeathers)
        citiesWeathers.addAll(londonWeathers)
        citiesWeathers.addAll(chicagoWeathers)
        if (::cityWeatherAdapter.isInitialized) {
            setLoading(false)
            binding.rvWeather.show()
            cityWeatherAdapter.submitList(citiesWeathers)
        }
    }

    private fun fillCityName(city: String) = WeatherInfo(city, VIEW_CITY_NAME_VIEW)
}