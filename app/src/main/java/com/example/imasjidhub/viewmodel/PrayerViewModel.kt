import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imasjidhub.model.PrayerItem
import com.example.imasjidhub.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class PrayerViewModel : ViewModel() {
    private val _prayerTimes = MutableStateFlow<PrayerItem?>(null)
    val prayerTimes = _prayerTimes.asStateFlow()

    fun fetchPrayerTimes() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getPrayerTimes()
                Log.d("DEBUG_API", "Response: ${response.items}")
                _prayerTimes.value = response.items.firstOrNull()
            } catch (e: Exception) {
                Log.e("DEBUG_API", "Error: ${e.message}", e)
            }
        }
    }

    fun fetchPrayerTimesByDate(date: Date) {
        fetchPrayerTimes()
    }
}
