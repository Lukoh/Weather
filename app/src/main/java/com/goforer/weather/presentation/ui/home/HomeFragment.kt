package com.goforer.weather.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.goforer.base.extension.*
import com.goforer.weather.R
import com.goforer.weather.data.Params
import com.goforer.weather.data.Query
import com.goforer.weather.data.source.model.entity.home.local.home.WeathersState
import com.goforer.weather.presentation.ui.home.adapter.CityWeatherAdapter
import com.goforer.weather.data.source.model.entity.home.response.CityWeatherResponse
import com.goforer.weather.data.source.model.entity.home.response.WeatherInfo
import com.goforer.weather.data.source.network.response.Status
import com.goforer.weather.databinding.FragmentHomeBinding
import com.goforer.weather.presentation.event.home.WeatherInfoEventBus
import com.goforer.weather.presentation.event.home.WeathersEventBus
import com.goforer.weather.presentation.stateholders.MediatorStatedViewModel
import com.goforer.weather.presentation.stateholders.home.GetCityWeatherViewModel
import com.goforer.weather.presentation.ui.BaseFragment
import com.goforer.weather.presentation.ui.MainActivity
import com.goforer.weather.presentation.ui.home.adapter.CityWeatherAdapter.Companion.VIEW_CITY_NAME_VIEW
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var cityWeatherAdapter: CityWeatherAdapter

    private var weathersState: WeathersState? = null

    private var linearLayoutManager: LinearLayoutManager? = null

    private var lastPosition = 0
    private var pickedPosition = NONE_PICKED_POSITION

    private var getCityWeatherViewModel: MediatorStatedViewModel? = null

    private var keyword = ""

    @Inject
    internal lateinit var getCityWeatherViewModelFactory: GetCityWeatherViewModel.AssistedVMFactory

    @Inject
    internal lateinit var weathersEventBus: WeathersEventBus

    @Inject
    internal lateinit var weatherInfoEventBus: WeatherInfoEventBus

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        load()
        setInputKeywordAddAfterTextChangedListener()
        setOnClickListener()
    }

    override fun onStop() {
        linearLayoutManager?.let {
            saveWeathersState(
                keyword,
                it.findFirstVisibleItemPosition(),
                it.findLastVisibleItemPosition(),
                pickedPosition
            )
        }

        super.onStop()
    }

    override fun onDestroyView() {
        binding.rvWeather.adapter = null

        super.onDestroyView()
    }

    private fun observe() {
        observeReloadWeathers()
    }

    private fun setCityWeatherAdapter() {
        cityWeatherAdapter = CityWeatherAdapter(mainActivity) { _, item, position ->
            pickedPosition = position
            showWeatherInfo(item, keyword, position)
        }

        setRecyclerView(mainActivity, cityWeatherAdapter)
    }

    private fun load() {
        view?.let {
            if (::cityWeatherAdapter.isInitialized)
                setCityWeatherAdapter()

            weathersState.isNull({
                with(binding) {
                    tvInputKeyword.isFocusable = true
                    tvHint.show()
                    ivClose.hide()
                    tvHint.text = getString(R.string.weather_search)
                }
            }, {
                reloadWeathers(it)
            })
        }
    }

    private fun setRecyclerView(context: Context, cityWeatherAdapter: CityWeatherAdapter) {
        with(binding) {
            rvWeather.apply {
                linearLayoutManager =
                    LinearLayoutManager(context as MainActivity, RecyclerView.VERTICAL, false)
                linearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
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

    private fun createViewModel(city: String) : MediatorStatedViewModel {
        val viewModel = GetCityWeatherViewModel.provideFactory(
            getCityWeatherViewModelFactory, Params(Query(city, 15))
        ).create(GetCityWeatherViewModel::class.java)
        getCityWeatherViewModel = viewModel

        return viewModel
    }

    private fun submitWeathers(viewModel: MediatorStatedViewModel, keyword: String, reloaded: Boolean) {
        view?.let {
            launchAndRepeatWithViewLifecycle {
                viewModel.value.collectLatest  { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                @Suppress("UNCHECKED_CAST")
                                val result = resource.data as? CityWeatherResponse

                                result?.let {
                                    setLoading(false)
                                    binding.rvWeather.show()
                                    if (!reloaded)
                                        it.list.add(0, fillCityName(keyword))

                                    if (::cityWeatherAdapter.isInitialized)
                                        cityWeatherAdapter.submitList(it.list)
                                }
                            }
                        }

                        Status.ERROR -> {
                            Timber.d("Error Code : %s", resource.errorCode)
                            Timber.d("Error Message : %s", resource.message)
                            setLoading(false)
                        }

                        Status.LOADING -> {
                            if (!reloaded)
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

    private fun reloadWeathers(weathersState: WeathersState) {
        view?.let {
            viewLifecycleOwner.lifecycleScope.launch {
                getCityWeatherViewModel = weathersState.viewModel as GetCityWeatherViewModel
                lastPosition = weathersState.lastVisibleItemPos
                setLoading(enabled = false)
                getCityWeatherViewModel?.let {
                    submitWeathers(it, weathersState.keyword, true)
                }
            }
        }
    }

    private fun observeReloadWeathers() {
        weathersEventBus.subscribe {
            weathersState = it
        }
    }

    private fun setInputKeywordAddAfterTextChangedListener() {
        with(binding) {
            tvInputKeyword.addAfterTextChangedListener(InputFilter.LengthFilter(200)) {
                if (tvInputKeyword.text.isNullOrEmpty()) {
                    tvHint.show()
                    ivClose.hide()
                } else {
                    tvHint.hide()
                    ivClose.show()
                }
            }

            tvInputKeyword.addTextChangedListener {
                if (it?.length!! > 0 && it.isNotEmpty()) {
                    for (span in it.getSpans(0, it.length, UnderlineSpan::class.java)) {
                        it.removeSpan(span)
                    }
                }
            }
        }
    }

    private fun setOnClickListener() {
        with(binding) {
            tvInputKeyword.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(tvInputKeyword.text?.trim().toString())
                }

                true
            }

            ivSearch.setSafeOnClickListener {
                val keyword = tvInputKeyword.text?.trim().toString()

                if (keyword.isNotEmpty()) {
                    doSearch(keyword)
                }
            }

            ivClose.setSafeOnClickListener {
                tvInputKeyword.text?.clear()
                tvInputKeyword.showKeyboard()
            }

            root.setSafeOnClickListener {
                hideKeyboard()
            }
        }
    }

    private fun saveWeathersState(keyword: String, firstPosition: Int, lastPosition: Int, pickedPosition: Int) {
        getCityWeatherViewModel?.let {
            createWeathersState(it, keyword, firstPosition, lastPosition, pickedPosition)
            weathersState?.let { state ->
                weathersEventBus.post(mainActivity.lifecycle, state)
            }
        }
    }

    private fun createWeathersState(
        viewModel: MediatorStatedViewModel,
        keyword: String,
        firstPosition: Int,
        lastPosition: Int,
        pickedPosition: Int
    ) {
        weathersState = WeathersState(viewModel, keyword, firstPosition, lastPosition, pickedPosition)
    }

    private fun fillCityName(city: String) = WeatherInfo(city, VIEW_CITY_NAME_VIEW)

    private fun doSearch(keyword: String) {
        setLoading(true)
        this.keyword = keyword
        binding.rvWeather.gone()
        if (::cityWeatherAdapter.isInitialized)
            cityWeatherAdapter.submitList(null)

        hideKeyboard()
        submitWeathers(createViewModel(keyword), keyword,false)
    }

    private fun showWeatherInfo(item: WeatherInfo, keyword: String, position: Int) {
        if (isAdded && isNavControllerInitialized()) {
            val direction = HomeFragmentDirections.actionHomeFragmentToWeatherInfoFragment(keyword, position)

            weatherInfoEventBus.post(mainActivity.lifecycle, item)
            navController.safeNavigate(direction.actionId, direction)
        }
    }
}