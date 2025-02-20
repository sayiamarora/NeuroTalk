package com.pulkitshubham.neurotalk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnVoiceCommand: Button = findViewById(R.id.btnVoiceCommand)
        val btnNotesApp: Button = findViewById(R.id.btnNotesApp)

        // Redirect to SpeechRecognitionActivity
        btnVoiceCommand.setOnClickListener {
            val intent = Intent(this, SpeechRecognitionActivity::class.java)
            startActivity(intent)
        }

        // Redirect to NotesActivity
        btnNotesApp.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }
    }
}
