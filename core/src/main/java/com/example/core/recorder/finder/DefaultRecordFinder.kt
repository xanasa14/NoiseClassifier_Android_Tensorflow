//package com.github.windsekirun.naraeaudiorecorder.recorder.finder
package com.example.core.recorder.finder
//import com.github.windsekirun.naraeaudiorecorder.recorder.AudioRecorder
import com.example.core.recorder.AudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.PcmAudioRecorder
import com.example.core.recorder.PcmAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.WavAudioRecorder
import com.example.core.recorder.WavAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.writer.RecordWriter
import com.example.core.writer.RecordWriter
import java.io.File

/**
 * Default settings of [RecordFinder]
 */
class DefaultRecordFinder : RecordFinder {

    /**
     * see [RecordFinder.find]
     */
    override fun find(extension: String, file: File, writer: RecordWriter): AudioRecorder {
        return when (extension) {
            "wav" -> WavAudioRecorder(file, writer)
            "pcm" -> PcmAudioRecorder(file, writer)
            else -> PcmAudioRecorder(file, writer)
        }
    }

}