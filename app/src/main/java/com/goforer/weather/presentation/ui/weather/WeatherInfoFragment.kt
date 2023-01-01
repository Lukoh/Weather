package com.goforer.weather.presentation.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.goforer.base.utils.time.TimeConverter
import com.goforer.weather.R
import com.goforer.weather.data.source.model.entity.home.response.WeatherInfo
import com.goforer.weather.databinding.FragmentWeatherInfoBinding
import com.goforer.weather.presentation.event.home.WeatherInfoEventBus
import com.goforer.weather.presentation.ui.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class WeatherInfoFragment: BaseFragment<FragmentWeatherInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWeatherInfoBinding
        get() = FragmentWeatherInfoBinding::inflate

    private val args: WeatherInfoFragmentArgs by navArgs()

    private lateinit var weatherInfo: WeatherInfo

    @Inject
    internal lateinit var weatherInfoEventBus: WeatherInfoEventBus

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeWeatherInf()
        setOnClickListener()
        Timber.d("Position : %s", args.position)
    }

    private fun observeWeatherInf() {
        weatherInfoEventBus.subscribe(mainActivity.lifecycle, true) { weatherInfo ->
            val sunriseTime = getString(R.string.sunrise_time, TimeConverter.convertTimestampToDateTime(weatherInfo.sunrise))
            val sunsetTime = getString(R.string.sunset_time, TimeConverter.convertTimestampToDateTime(weatherInfo.sunset))

            this.weatherInfo = weatherInfo
            binding.tvHome.text = context.getString(R.string.weather_day, args.city, TimeConverter.convertTimestampToDate(weatherInfo.dt))
            Toast.makeText(this.context, sunriseTime, Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                delay(2000L)
                Toast.makeText(this@WeatherInfoFragment.context, sunsetTime, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setOnClickListener() {
        with(binding) {
            arrowContainer.setOnClickListener {
                handleBackPressed()
                if (NavHostFragment.findNavController(this@WeatherInfoFragment)
                        .popBackStack()
                        .not()
                ) {
                    activity?.finishAndRemoveTask()
                }
            }
        }
    }
}