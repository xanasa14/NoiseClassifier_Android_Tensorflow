//package com.github.windsekirun.naraeaudiorecorder.recorder
package com.example.core.recorder
//import com.github.windsekirun.naraeaudiorecorder.writer.RecordWriter
import com.example.core.writer.RecordWriter
import java.io.File

/**
 * [AudioRecorder] for record audio and save in pcm file
 */
class PcmAudioRecorder(file: File, recordWriter: RecordWriter) : DefaultAudioRecorder(file, recordWriter)