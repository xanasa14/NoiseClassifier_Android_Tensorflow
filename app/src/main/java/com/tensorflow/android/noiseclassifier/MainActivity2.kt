package com.tensorflow.android.noiseclassifier
//package com.github.windsekirun.naraeaudiorecorder.sample
//package com.example.sample
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.ObservableInt
//import com.github.windsekirun.naraeaudiorecorder.NaraeAudioRecorder
import com.example.core.NaraeAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.chunk.AudioChunk
import com.example.core.chunk.AudioChunk
//import com.github.windsekirun.naraeaudiorecorder.config.AudioRecordConfig
import com.example.core.config.AudioRecordConfig
//import com.github.windsekirun.naraeaudiorecorder.constants.LogConstants
import com.example.core.constants.LogConstants
//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.FFmpegAudioRecorder
import com.example.ffmpeg_recorder.FFmpegAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.FFmpegRecordFinder
import com.example.ffmpeg_recorder.FFmpegRecordFinder
//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.model.FFmpegConvertState
import com.example.ffmpeg_recorder.model.FFmpegConvertState
//import com.github.windsekirun.naraeaudiorecorder.model.RecordMetadata
import com.example.core.model.RecordMetadata
//import com.github.windsekirun.naraeaudiorecorder.model.RecordState
import com.example.core.model.RecordState
//


//import com.github.windsekirun.naraeaudiorecorder.sample.databinding.MainActivityBinding
//import com.example.sample.databinding.ActivityMainBinding
import com.tensorflow.android.noiseclassifier.databinding.ActivityMain2Binding;

//import com.github.windsekirun.naraeaudiorecorder.source.DefaultAudioSource
import com.example.core.source.DefaultAudioSource
//import com.github.windsekirun.naraeaudiorecorder.source.NoiseAudioSource
import com.example.core.source.NoiseAudioSource
//import com.tensorflow.android.noiseclassifier.databinding.ActivityMain2Binding
import pyxis.uzuki.live.richutilskt.utils.*
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.arrayOf
//2
class MainActivity2 : AppCompatActivity() {
    val sourceCheckedPosition = ObservableInt(0)
    val recorderCheckedPosition = ObservableInt(0)

    private var recordInitialized: Boolean = false
    private var recordMetadata: RecordMetadata? = null
    private var destFile: File? = null
    private var ffmpegMode: Boolean = false

    private val audioRecorder = NaraeAudioRecorder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this)).apply {
//           activity = this@MainActivity
        val binding = ActivityMain2Binding.inflate(LayoutInflater.from(this)).apply {
//            activity = this@Main2Activity
            activity = this@MainActivity2
        }
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(
                    this,
                    LogConstants.PERMISSION_DENIED,
                    Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    fun clickStart(view: View) {
        val extensions = ".wav"
        /*val extensions = when (recorderCheckedPosition.get()) {
            0 -> ".pcm"
            1 -> ".wav"
            2 -> ".mp3"
            3 -> ".aac"
            4 -> ".m4a"
            5 -> ".wma"
            6 -> ".flac"
            else -> ""
        }*/
        if (extensions.isEmpty()) return

        ffmpegMode = recorderCheckedPosition.get() >= 2
        val fileName = System.currentTimeMillis().asDateString()
        destFile = File(
            Environment.getExternalStorageDirectory(),
            "/NaraeAudioRecorder/$fileName$extensions"
        )
        if (destFile == null) return
        destFile?.parentFile?.mkdir()

        val recordConfig = AudioRecordConfig.defaultConfig()

        val audioSource = when (sourceCheckedPosition.get()) {
            1 -> NoiseAudioSource(recordConfig)
            else -> DefaultAudioSource(recordConfig)
        }

        audioRecorder.create(FFmpegRecordFinder::class.java) {
            //this.destFile = this@Main2Activity.destFile
            this.destFile = this@MainActivity2.destFile
            this.recordConfig = recordConfig
            this.audioSource = audioSource
            this.chunkAvailableCallback = { chunkAvailable(it) }
            this.silentDetectedCallback = { silentDetected(it) }
            this.timerCountCallback = { current, max -> timerChanged(current, max) }
            this.debugMode = true
        }

        if (ffmpegMode) {
            val recorder: FFmpegAudioRecorder =
                audioRecorder.getAudioRecorder() as? FFmpegAudioRecorder ?: return
            recorder.setContext(this)
            recorder.setOnConvertStateChangeListener {
                ffmpegConvertStateChanged(it)
            }
        }

        audioRecorder.setOnRecordStateChangeListener { recordStateChanged(it) }
        audioRecorder.startRecording(this)
        recordInitialized = true
    }

    fun clickStop(view: View) {
        if (!recordInitialized) {
            toast("Press Start button first.")
            return
        }

        audioRecorder.stopRecording()
        if (!ffmpegMode) {
            recordMetadata = audioRecorder.retrieveMetadata(destFile ?: File(""))
            alert("Saved on ${destFile?.absolutePath}")
        }
    }

    fun clickResume(view: View) {
        if (!recordInitialized) {
            toast("Press Start button first.")
            return
        }

        audioRecorder.resumeRecording()
    }

    fun clickPause(view: View) {
        if (!recordInitialized) {
            toast("Press Start button first.")
            return
        }

        audioRecorder.pauseRecording()
    }

    fun clickPlay(view: View) {
        if (!recordInitialized) {
            toast("Press Start button first")
            return
        }

        val uri = if (Build.VERSION.SDK_INT >= 24) {
            val authority = "$packageName.fileprovider"
            FileProvider.getUriForFile(this, authority, destFile ?: File(""))
        } else {
            Uri.fromFile(destFile)
        }

        val intent = Intent(Intent.ACTION_VIEW)
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(destFile?.extension)
        intent.apply {
            setDataAndType(uri, mimeType)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, "Open with...")
        startActivity(chooser)
    }

    private fun chunkAvailable(audioChunk: AudioChunk) {
        Log.d(
            TAG,
            "chunkAvailable: maxAmp: ${audioChunk.getMaxAmplitude()}, readCount: ${audioChunk.getReadCount()}"
        )
    }

    private fun silentDetected(silentTime: Long) {
        Log.d(TAG, "silentDetected: time: $silentTime")
    }

    private fun timerChanged(currentTime: Long, maxTime: Long) {
        Log.d(TAG, "timerChanged: current: $currentTime max: $maxTime")
    }

    private fun ffmpegConvertStateChanged(convertState: FFmpegConvertState) {
        if (convertState == FFmpegConvertState.SUCCESS) {
            recordMetadata = audioRecorder.retrieveMetadata(destFile ?: File(""))
            alert("Saved on ${destFile?.absolutePath}")
        }

        Log.d(TAG, "ffmpegConvertStateChanged: ${convertState.name}")

        runOnUiThread { toast("FFmpegConvertState: ${convertState.name}") }
    }

    private fun recordStateChanged(recordState: RecordState) {
        Log.d(TAG, "recordStateChanged: ${recordState.name}")

        runOnUiThread { toast("RecordState: ${recordState.name}") }
    }

    companion object {
        const val TAG = "MainActivity2"
        const val PERMISSIONS_REQUEST_CODE = 1
    }
}
