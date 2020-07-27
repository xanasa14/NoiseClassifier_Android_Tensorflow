//package com.github.windsekirun.naraeaudiorecorder.ffmpeg
package com.example.ffmpeg_recorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.AudioRecorder
import com.example.core.recorder.AudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.PcmAudioRecorder
import com.example.core.recorder.PcmAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.WavAudioRecorder
import com.example.core.recorder.WavAudioRecorder
//import com.github.windsekirun.naraeaudiorecorder.recorder.finder.RecordFinder
import com.example.core.recorder.finder.RecordFinder
//import com.github.windsekirun.naraeaudiorecorder.writer.RecordWriter
import com.example.core.writer.RecordWriter
import java.io.File

/**
 * Default + FFmpeg settings of [RecordFinder]
 */
class FFmpegRecordFinder : RecordFinder {

    /**
     * see [RecordFinder.find]
     */
    override fun find(extension: String, file: File, writer: RecordWriter): AudioRecorder {
        return when (extension) {
            "wav" -> WavAudioRecorder(file, writer)
            "pcm" -> PcmAudioRecorder(file, writer)
            "aac" -> FFmpegAudioRecorder(file, writer)
            "mp3" -> FFmpegAudioRecorder(file, writer)
            "m4a" -> FFmpegAudioRecorder(file, writer)
            "wma" -> FFmpegAudioRecorder(file, writer)
            "flac" -> FFmpegAudioRecorder(file, writer)
            else -> PcmAudioRecorder(file, writer)
        }
    }

}