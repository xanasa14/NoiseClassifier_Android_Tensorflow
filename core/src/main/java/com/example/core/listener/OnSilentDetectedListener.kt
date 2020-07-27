//package com.github.windsekirun.naraeaudiorecorder.listener
package com.example.core.listener
/**
 * Listener for handling Silent event of recording while
 * using [com.github.windsekirun.naraeaudiorecorder.source.NoiseAudioSource]
 */
interface OnSilentDetectedListener {

    /**
     * Invoke when silence measured.
     */
    fun onSilence(silenceTime: Long)
}