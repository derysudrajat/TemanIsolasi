package id.temanisolasi

import com.google.firebase.Timestamp
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.toTimeStamp
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val today = "12/08/2021".toTimeStamp(DateFormat.SIMPLE)
        val start = "11/08/2021".toTimeStamp(DateFormat.SIMPLE)
        val diff = today.toDate().time - start.toDate().time
        val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
        println(day)
    }

    @Test
    fun testTime() {
        val cal = Calendar.getInstance().apply { time = Timestamp.now().toDate() }
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        println("hour = $hour")
    }
}