import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vm.CountViewModel

@Composable
fun Content() {
    val countViewModel = viewModel { CountViewModel() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = countViewModel.count.toString(),
            color = if (countViewModel.count < 0) Color.Red else Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text("Current Count")

        Row(modifier = Modifier.padding(vertical = 12.dp)) {
            Button(
                onClick = { countViewModel.dec() },
                modifier = Modifier.padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            ) { Text("Decrement") }

            Button(
                onClick = { countViewModel.inc() },
                modifier = Modifier.padding(horizontal = 8.dp),
            ) { Text("Increment") }
        }
    }
}