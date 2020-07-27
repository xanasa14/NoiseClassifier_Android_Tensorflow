//package com.github.windsekirun.naraeaudiorecorder.ffmpeg.listener
package com.example.ffmpeg_recorder.listener

//import com.github.windsekirun.naraeaudiorecorder.ffmpeg.model.FFmpegConvertState
import com.example.ffmpeg_recorder.model.FFmpegConvertState
/**
 * Listener for handling state changes
 */
interface OnConvertStateChangeListener {

    /**
     * Call when [FFmpegConvertState] is changed
     */
    fun onState(state: FFmpegConvertState)
}