package com.pulkitshubham.neurotalk

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.text.SimpleDateFormat
import java.util.*

class SpeechRecognitionActivity : AppCompatActivity() {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var tvResult: TextView
    private lateinit var btnStartListening: Button
    private lateinit var btnStopListening: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_recognition)

        tvResult = findViewById(R.id.tvResult)
        btnStartListening = findViewById(R.id.btnStartListening)
        btnStopListening = findViewById(R.id.btnStopListening)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        btnStartListening.setOnClickListener { startListening() }
        btnStopListening.setOnClickListener { stopListening() }

        requestPermissions()
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CALL_PHONE), 1)
        }
    }

    private fun startListening() {
        tvResult.text = "" // Clear previous text

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Toast.makeText(this@SpeechRecognitionActivity, "Speech Recognition Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches[0].lowercase(Locale.getDefault())
                    tvResult.text = "Transcribed: $spokenText"
                    processCommand(spokenText)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    private fun stopListening() {
        speechRecognizer.stopListening()
        Toast.makeText(this, "Listening Stopped", Toast.LENGTH_SHORT).show()
    }

    private fun processCommand(command: String) {
        when {
            command.contains("open youtube", ignoreCase = true) -> openYouTube()
            command.contains("open camera", ignoreCase = true) -> openCamera()
            command.contains("make a call", ignoreCase = true) -> makeCall("1234567890")
            command.contains("open calendar", ignoreCase = true) -> openCalendar()
            command.contains("schedule a meeting", ignoreCase = true) -> extractMeetingDetails(command)
            else -> Toast.makeText(this, "You said: $command", Toast.LENGTH_SHORT).show()
        }
    }

    private fun extractMeetingDetails(command: String) {
        val datePattern = """(\d{1,2}(st|nd|rd|th)?\s+(January|February|March|April|May|June|July|August|September|October|November|December))""".toRegex(RegexOption.IGNORE_CASE)
        val timePattern = """(\d{1,2}(:\d{2})?\s?(AM|PM|am|pm)?)""".toRegex()

        val dateMatch = datePattern.find(command)
        val timeMatch = timePattern.find(command)

        val date = dateMatch?.value?.replace(Regex("(st|nd|rd|th)"), "")
        val time = timeMatch?.value ?: "10:00 AM" // Default time if not found

        if (date != null) {
            Log.d("SpeechRecognition", "Extracted Date: $date, Extracted Time: $time")
            scheduleMeeting(date, time)
        } else {
            Toast.makeText(this, "Couldn't extract date/time. Please try again!", Toast.LENGTH_LONG).show()
        }
    }

    private fun scheduleMeeting(date: String, time: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, "Meeting")
            putExtra(CalendarContract.Events.DESCRIPTION, "Scheduled via Voice Command")
            putExtra(CalendarContract.Events.EVENT_LOCATION, "Online / Office")
            putExtra(CalendarContract.Events.ALL_DAY, false)

            val dateTimeMillis = parseDateTimeToMillis(date, time)
            if (dateTimeMillis != null) {
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateTimeMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateTimeMillis + 60 * 60 * 1000) // 1-hour duration
            }
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No compatible calendar app found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseDateTimeToMillis(date: String, time: String): Long? {
        val dateTimeString = "$date $time".trim()
        val format = SimpleDateFormat("d MMMM hh:mm a", Locale.getDefault())

        return try {
            val dateObj = format.parse(dateTimeString)
            dateObj?.time
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun openYouTube() {
        val intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
        if (intent != null) startActivity(intent) else startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com")))
    }

    private fun openCamera() {
        startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
    }

    private fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Permission Denied for Calls", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCalendar() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.calendar/time/")))
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
    }
}
