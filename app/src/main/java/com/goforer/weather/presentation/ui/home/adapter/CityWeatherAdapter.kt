package com.goforer.weather.presentation.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.goforer.base.extension.getHighlightSpanMap
import com.goforer.base.extension.setSafeOnClickListener
import com.goforer.base.extension.setSpans
import com.goforer.base.utils.temperature.DegreeConverter
import com.goforer.base.utils.time.TimeConverter
import com.goforer.base.view.holder.BaseViewHolder
import com.goforer.weather.R
import com.goforer.weather.data.source.model.entity.home.response.WeatherInfo
import com.goforer.weather.databinding.ItemCityNameBinding
import com.goforer.weather.databinding.ItemWeatherBinding

class CityWeatherAdapter(
    private val context: Context,
    private inline val onClicked: (itemView: View, item: WeatherInfo, position: Int) -> Unit,
) : ListAdapter<WeatherInfo, BaseViewHolder<WeatherInfo>>(CityWeatherDiffUtil) {
    private var _binding: ItemWeatherBinding? = null
    private val binding get() = _binding!!
    private var _bindingCityName: ItemCityNameBinding? = null
    private val bindingCityName get() = _bindingCityName!!

    companion object {
        internal const val VIEW_CITY_NAME_VIEW = 0
        internal const val VIEW_WEATHER_TYPE = 1

        private const val WEATHER_CLEAR_SKY = "clear sky"
        private const val WEATHER_FEW_CLOUDS = "few clouds"
        private const val WEATHER_SCATTERED_CLOUDS = "scattered clouds"
        private const val WEATHER_BROKEN_CLOUDS = "broken clouds"
        private const val WEATHER_OVERCAST_CLOUDS = "overcast clouds"
        private const val WEATHER_DRIZZLE = "drizzle"
        private const val WEATHER_RAIN = "rain"
        private const val WEATHER_LIGHT_RAIN = "light rain"
        private const val WEATHER_MODERATE_RAIN = "moderate rain"
        private const val WEATHER_SHOWER_RAIN = "shower rain"
        private const val WEATHER_HEAVY_INTENSITY_RAIN = "heavy intensity rain"
        private const val WEATHER_THUNDER_STORM = "thunderstorm"
        private const val WEATHER_SNOW = "snow"
        private const val WEATHER_LIGHT_SNOW = "light snow"
        private const val WEATHER_MIST =  "mist"
    }

    object CityWeatherDiffUtil : DiffUtil.ItemCallback<WeatherInfo>() {
        override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WeatherInfo> {
        val contextThemeWrapper = ContextThemeWrapper(context, R.style.AppTheme)

        return when(viewType) {
            VIEW_CITY_NAME_VIEW -> {
                _bindingCityName = null
                _bindingCityName = ItemCityNameBinding.inflate(
                    LayoutInflater.from(contextThemeWrapper), parent, false
                )

                CityNameItemHolder(bindingCityName)
            }
            VIEW_WEATHER_TYPE -> {
                _binding = null
                _binding = ItemWeatherBinding.inflate(
                    LayoutInflater.from(contextThemeWrapper), parent, false
                )

                WeatherItemHolder(binding, this)
            }
            else -> {
                _binding = null
                _binding = ItemWeatherBinding.inflate(
                    LayoutInflater.from(contextThemeWrapper), parent, false
                )

                WeatherItemHolder(binding, this)
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position)?.viewType!!

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherInfo>, position: Int) {
        getItem(position)?.let {
            holder.onBindItemHolder(holder, it, position)
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<WeatherInfo>) {
        super.onViewDetachedFromWindow(holder)

        _binding = null
        _bindingCityName = null
    }

    class WeatherItemHolder(
        private val binding: ItemWeatherBinding,
        private val adapter: CityWeatherAdapter
    ) : BaseViewHolder<WeatherInfo>(binding.root) {
        override fun onBindItemHolder(
            holder: BaseViewHolder<WeatherInfo>,
            item: WeatherInfo,
            position: Int
        ) {
            with(binding) {
                setDate(item.dt, position)
                item.weather?.let {
                    val resId = when(it[0].description) {
                        WEATHER_CLEAR_SKY -> R.drawable.ic_day_sunny
                        WEATHER_FEW_CLOUDS -> R.drawable.ic_day_cloudy
                        WEATHER_SCATTERED_CLOUDS -> R.drawable.ic_day_cloudy_windy
                        WEATHER_BROKEN_CLOUDS -> R.drawable.ic_day_cloudy
                        WEATHER_OVERCAST_CLOUDS -> R.drawable.ic_day_cloudy
                        WEATHER_DRIZZLE -> R.drawable.ic_day_sleet
                        WEATHER_RAIN -> R.drawable.ic_day_rain
                        WEATHER_LIGHT_RAIN -> R.drawable.ic_day_sprinkle
                        WEATHER_MODERATE_RAIN -> R.drawable.ic_day_rain_mix
                        WEATHER_SHOWER_RAIN -> R.drawable.ic_day_showers
                        WEATHER_HEAVY_INTENSITY_RAIN -> R.drawable.ic_day_storm_showers
                        WEATHER_THUNDER_STORM -> R.drawable.ic_day_thunderstorm
                        WEATHER_SNOW -> R.drawable.ic_day_snow_wind
                        WEATHER_LIGHT_SNOW -> R.drawable.ic_day_snow
                        WEATHER_MIST -> R.drawable.ic_day_fog
                        else -> R.drawable.ic_day_sunny
                    }

                    ivPicture.setImageResource(resId)
                    tvWeather.text = it[0].description
                }

                item.temp?.let {
                    tvMaxDegree.text = context.getString(R.string.max, DegreeConverter.covertCelsius(it.max))
                    tvMinDegree.text = context.getString(R.string.min, DegreeConverter.covertCelsius(it.min))
                }

                root.setSafeOnClickListener {
                    adapter.onClicked(it, item, position)
                }
            }
        }

        override fun onItemSelected() {
            TODO("Not yet implemented")
        }

        override fun onItemClear() {
            TODO("Not yet implemented")
        }

        private fun setDate(dateTime: Long, position: Int){
            when(position) {
                1 -> {
                    with(binding) {
                        tvDate.text = context.getString(R.string.today, TimeConverter.convertTimestampToDate(dateTime))
                        setSpan(tvDate, "Today")
                    }
                }
                2 -> {
                    with(binding) {
                        binding.tvDate.text =  context.getString(R.string.tomorrow, TimeConverter.convertTimestampToDate(dateTime))
                        setSpan(tvDate, "Tomorrow")
                    }
                }
                else -> {
                    binding.tvDate.text = TimeConverter.convertTimestampToDate(dateTime)
                }
            }
        }

        private fun setSpan(editText: TextView, text: String) {
            editText.setSpans(1.05F,
                getHighlightSpanMap(
                    text,
                    ContextCompat.getColor(
                        editText.context,
                        R.color.colorText3
                    ),
                    Typeface.create("sans-serif", Typeface.NORMAL),
                    false
                ) {

                }
            )
        }
    }

    class CityNameItemHolder(
        private val binding: ItemCityNameBinding
    ) : BaseViewHolder<WeatherInfo>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun onBindItemHolder(
            holder: BaseViewHolder<WeatherInfo>,
            item: WeatherInfo,
            position: Int
        ) {
            with(binding) {
                tvCity.text = item.city
            }
        }

        override fun onItemSelected() {
            //TODO("Not yet implemented")
        }

        override fun onItemClear() {
            //TODO("Not yet implemented")
        }
    }
}