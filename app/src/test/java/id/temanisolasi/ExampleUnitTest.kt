package id.temanisolasi

import android.icu.text.MessageFormat
import com.google.firebase.Timestamp
import id.temanisolasi.utils.DataHelpers
import id.temanisolasi.utils.DateFormat
import id.temanisolasi.utils.Helpers.formatDate
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
        val today = Timestamp.now().formatDate(DateFormat.SIMPLE)?.toTimeStamp(DateFormat.SIMPLE)
        val start = "31/08/2021".toTimeStamp(DateFormat.SIMPLE)
        val diff = today?.toDate()?.time?.minus(start.toDate().time)
        val day = diff?.let { TimeUnit.DAYS.convert(it, TimeUnit.MILLISECONDS) }?.plus(1)
        println(day)
    }

    @Test
    fun testDay() {
        repeat(14) {
            print(it)
            print(" ")
        }
    }

    @Test
    fun testRandom() {
        val list = (0..30)
        println("Pick one : ${list.shuffled().take(1)[0]}")
    }

    @Test
    fun testTime() {
        val cal = Calendar.getInstance().apply { time = Timestamp.now().toDate() }
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        println("hour = $hour")
    }

    @Test
    fun testNotification() {
        val name = "Sayang"
        val message = DataHelpers.notificationTitle[0]
        println("Notification = ${MessageFormat(message).format({ name })}")
    }
}