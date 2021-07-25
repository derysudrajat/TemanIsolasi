package id.temanisolasi.ui.base.home.condition

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import id.temanisolasi.R
import id.temanisolasi.databinding.FragmentTemperatureBinding

class LineChartViewHolder(
    private val context: Context, private val binding: FragmentTemperatureBinding
) {
    fun setDataChart(dataTemp: MutableList<Float>) {
        val dataSet = LineDataSet(setupData(dataTemp), "Suhu")
            .apply { setLineChartStyle(this, R.color.secondary) }
        val lineData = LineData(dataSet)
        binding.mainChart.apply {
            initChart(this)
            data = lineData
            invalidate()
        }
    }

    private fun setupData(source: MutableList<Float>): MutableList<Entry> {
        val entryList = mutableListOf<Entry>()
        source.forEachIndexed { index, data ->
            entryList.add(Entry(index.toFloat(), data))
        }
        return entryList
    }

    private fun setLineChartStyle(lineDataSet: LineDataSet, @ColorRes colorResId: Int) {
        with(lineDataSet) {
            color = ContextCompat.getColor(context, colorResId)
            lineWidth = 5f
            circleRadius = 0f
            setDrawCircles(false)
            setDrawCircleHole(false)
            setCircleColor(ContextCompat.getColor(context, colorResId))
            valueTextSize = 0f
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(false)
            fillColor = ContextCompat.getColor(context, colorResId)
            fillAlpha = 100
        }
    }

    private fun initChart(lineChart: LineChart) {
        with(lineChart) {
            animateX(1500)
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.axisMinimum = 34f
            isDragEnabled = false
            setTouchEnabled(false)
            setScaleEnabled(false)
        }
    }
}