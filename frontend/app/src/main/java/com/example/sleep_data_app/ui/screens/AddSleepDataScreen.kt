package com.example.sleep_data_app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sleep_data_app.data.model.SleepData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSleepDataScreen(
    onBackClick: () -> Unit,
    onSave: (SleepData) -> Unit,
    isLoading: Boolean,
    error: String?
) {
    var bedtime by remember { mutableStateOf("22:00") }
    var wakeupTime by remember { mutableStateOf("07:00") }
    var mood by remember { mutableIntStateOf(5) }
    var energy by remember { mutableIntStateOf(5) }
    var focus by remember { mutableIntStateOf(5) }
    var restedLevel by remember { mutableIntStateOf(5) }
    var stressLevel by remember { mutableIntStateOf(5) }
    var dreamed by remember { mutableStateOf(false) }
    var wokeUpAtNight by remember { mutableStateOf(false) }
    var caffeineAfter6pm by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva medici\u00f3n") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Horarios",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = bedtime,
                onValueChange = { bedtime = it },
                label = { Text("Hora de dormir (HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )

            OutlinedTextField(
                value = wakeupTime,
                onValueChange = { wakeupTime = it },
                label = { Text("Hora de despertar (HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )

            HorizontalDivider()

            Text(
                text = "Evaluaci\u00f3n (1-10)",
                style = MaterialTheme.typography.titleMedium
            )

            SliderField(
                label = "Mood",
                value = mood,
                onValueChange = { mood = it }
            )

            SliderField(
                label = "Energy",
                value = energy,
                onValueChange = { energy = it }
            )

            SliderField(
                label = "Focus",
                value = focus,
                onValueChange = { focus = it }
            )

            SliderField(
                label = "Nivel de descanso",
                value = restedLevel,
                onValueChange = { restedLevel = it }
            )

            SliderField(
                label = "Nivel de estr\u00e9s",
                value = stressLevel,
                onValueChange = { stressLevel = it }
            )

            HorizontalDivider()

            Text(
                text = "Extras",
                style = MaterialTheme.typography.titleMedium
            )

            SwitchField(
                label = "\u00bfSo\u00f1aste?",
                checked = dreamed,
                onCheckedChange = { dreamed = it }
            )

            SwitchField(
                label = "\u00bfDespertaste durante la noche?",
                checked = wokeUpAtNight,
                onCheckedChange = { wokeUpAtNight = it }
            )

            SwitchField(
                label = "\u00bfCafe\u00edna despu\u00e9s de las 6pm?",
                checked = caffeineAfter6pm,
                onCheckedChange = { caffeineAfter6pm = it }
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentario (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            if (error != null) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    val sleepData = SleepData(
                        userId = "",
                        bedtime = bedtime,
                        wakeupTime = wakeupTime,
                        mood = mood,
                        energy = energy,
                        focus = focus,
                        dreamed = dreamed,
                        wokeUpAtNight = wokeUpAtNight,
                        restedLevel = restedLevel,
                        stressLevel = stressLevel,
                        caffeineAfter6pm = caffeineAfter6pm,
                        comment = comment.ifBlank { null }
                    )
                    onSave(sleepData)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !isLoading && bedtime.isNotBlank() && wakeupTime.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SliderField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(text = "$value/10", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 1f..10f,
            steps = 8
        )
    }
}

@Composable
private fun SwitchField(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
