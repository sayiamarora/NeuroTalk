# NeuroTalk: Smart Voice Assistant for Professionals

## 📌 Project Overview
NeuroTalk is an intelligent voice assistant designed for professionals to streamline their workflow by enabling hands-free note-taking, scheduling, and task execution. Unlike traditional voice assistants, NeuroTalk prioritizes user privacy by not storing data and instead utilizes **Android Intents** to interact with third-party applications like Phone, Camera, Google Keep, and Calendar.

## 🚀 Features
- 🎙 **Real-Time Speech-to-Text Transcription** – Converts speech into text accurately.
- ☎ **Make Phone Calls** – Initiates phone calls using voice commands.
- 📷 **Open Camera for Quick Capture** – Opens the camera app instantly.
- 📝 **Take Meeting Notes** – Redirects to Google Keep for note-taking.
- 📅 **Schedule Meetings** – Schedules meetings in Google Calendar.

## 🛠 Technologies Used
- **Android Development (Kotlin)** – Core programming language for app development.
- **Google Speech-to-Text API** – Converts spoken language into text in real-time.
- **Natural Language Processing (NLP)** – Extracts key actions from voice commands.
- **Android Intents** – Facilitates redirection to third-party applications.

## 🎥 Demo
### 1. Real-Time Speech-to-Text
- Tap 'Record' & speak.
- Speech is converted to text in real-time.

### 2. Make a Phone Call
- Example: "Make a call to 78XXXXXXXX."
- Redirects to the Phone app with the given number dialed.

### 3. Open Camera
- Example: "Open Camera."
- Launches the Camera app immediately.

### 4. Take Meeting Notes
- Speak your notes naturally, and they will be transcribed into text.
- Redirects to Google Keep with a pre-filled note.

### 5. Schedule a Meeting
- Example: "Schedule a meeting on October 12 at 3 PM."
- Opens Google Calendar with event details auto-filled.

## 🏗 How It Works
### 1. **Speech Recognition**
   - Google Speech-to-Text API transcribes voice input.
### 2. **Action Extraction**
   - NLP identifies key phrases like "Call," "Take a note," "Schedule."
### 3. **App Redirection**
   - Android Intents route the user to the appropriate app.

**Architecture Flow:**
```
Voice Input → Speech-to-Text → NLP Processing → Extracted Intent → App Redirection
```

## 🔧 Challenges & Solutions
### 1. Handling Variations in Speech
- Used **pattern matching & NLP models** to detect multiple ways of saying the same command.

### 2. Ensuring Smooth Redirection
- Managed **Android Intents efficiently** to handle permissions and app launches seamlessly.

## 📌 Future Enhancements
- 🔹 **Integration with WhatsApp & Slack** for broader communication.
- 🔹 **AI-driven Smart Replies** to enhance automation.

## 📜 Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/sayiamarora/NeuroTalk.git
   ```
2. Open the project in **Android Studio**.
3. Configure API keys for **Google Speech-to-Text**.
4. Run the application on an emulator or a physical device.

## 🏆 Conclusion
NeuroTalk is an innovative smart assistant designed to improve productivity for professionals by offering a hands-free and seamless experience. With its real-time transcription, action extraction, and smooth app redirection, it simplifies daily tasks while prioritizing user privacy. 🚀
