package com.example.queenscorner.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.queenscorner.R
import com.example.queenscorner.ui.theme.QueensCornerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Button to go to board activity
        val newGameButton = findViewById<Button>(R.id.new_game)
        newGameButton.setOnClickListener{
            showGameSettingsDialog(this, it)
        }
    }
}

fun showGameSettingsDialog(context: Context, view: View) {
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_game_settings, null)

    val settingsDialog = AlertDialog.Builder(context)
        .setTitle("Game Settings")
        .setView(dialogView)
        .setPositiveButton("OK") { _, _ ->
            // Extract selected settings from dialogView
            val zombieCheckbox = dialogView.findViewById<CheckBox>(R.id.option1_checkbox)
            val zombieSelected = zombieCheckbox.isChecked

            val numPlayersEditText = dialogView.findViewById<EditText>(R.id.num_players_edittext)
            val numPlayersText = numPlayersEditText.text.toString()
            val numPlayers = if (numPlayersText.isNotEmpty()) numPlayersText.toInt() else 4

            // Start BoardActivity with selected settings
            val intent = Intent(context, BoardActivity::class.java).apply {
                putExtra("zombie", zombieSelected)
                putExtra("numPlayers", numPlayers)

            }
            context.startActivity(intent)
        }
        .setNegativeButton("Cancel", null)
        .create()

    settingsDialog.show()
}
