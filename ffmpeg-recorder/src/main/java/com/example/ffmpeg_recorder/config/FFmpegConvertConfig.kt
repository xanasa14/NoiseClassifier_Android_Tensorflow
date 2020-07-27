//package com.github.windsekirun.naraeaudiorecorder.ffmpeg.config
package com.example.ffmpeg_recorder.config
//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.model.FFmpegBitRate
import com.example.ffmpeg_recorder.model.FFmpegBitRate
//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.model.FFmpegSamplingRate
import com.example.ffmpeg_recorder.model.FFmpegSamplingRate
/**
 * Data class for convert config in FFmpeg
 *
 * @param bitRate Bitrate settings
 * @param samplingRate Sampling rate settings
 * @param mono true if channel is mono
 */
data class FFmpegConvertConfig(val bitRate: FFmpegBitRate, val samplingRate: FFmpegSamplingRate, val mono: Boolean) {

    companion object {
        @JvmStatic
        fun defaultConfig() = FFmpegConvertConfig(FFmpegBitRate.def, FFmpegSamplingRate.ORIGINAL, true)
    }
}