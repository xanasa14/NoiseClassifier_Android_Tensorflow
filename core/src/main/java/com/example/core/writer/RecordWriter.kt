//package com.github.windsekirun.naraeaudiorecorder.writer
package com.example.core.writer
//import com.github.windsekirun.naraeaudiorecorder.source.AudioSource
import com.example.core.source.AudioSource
import java.io.OutputStream

/**
 * Record control for reading from [android.media.AudioRecord] and write to [OutputStream]
 */
interface RecordWriter {

    /**
     * Start recording feature using [getAudioSource]
     *
     * Basically, it read from [android.media.AudioRecord] and write to [OutputStream]
     */
    fun startRecording(outputStream: OutputStream)

    /**
     * Stop recording feature
     */
    fun stopRecording()

    /**
     * get [AudioSource] which is used for reading from [android.media.AudioRecord]
     */
    fun getAudioSource(): AudioSource
}