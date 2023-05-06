import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appointment.InsertionUtil
import com.google.firebase.FirebaseApp
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsertionUtilTest {

    @Test
    fun insertData() {
        val empName = "John Doe"
        val empAge = "30"
        val docName = "Dr. Smith"

        val context = ApplicationProvider.getApplicationContext<Context>()
        FirebaseApp.initializeApp(context)

        InsertionUtil.insertData(empName, empAge, docName)
    }
}