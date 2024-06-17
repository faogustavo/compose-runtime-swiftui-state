package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dev.valvassori.state.ObservableViewModel

class CountViewModel : ObservableViewModel() {
    var count: Int by mutableStateOf(0)
        private set

    fun inc() {
        count += 1
    }

    fun dec() {
        count -= 1
    }
}