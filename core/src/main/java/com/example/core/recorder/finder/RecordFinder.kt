//package com.github.windsekirun.naraeaudiorecorder.recorder.finder
package com.example.core.recorder.finder
//import com.github.windsekirun.naraeaudiorecorder.recorder.AudioRecorder
import com.example.core.recorder.AudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.writer.RecordWriter
import com.example.core.writer.RecordWriter
import java.io.File

/**
 * find proper [AudioRecorder] class which condition
 */
interface RecordFinder {

    /**
     * find [AudioRecorder] with given [extension]
     */
    fun find(extension: String, file: File, writer: RecordWriter): AudioRecorder
}