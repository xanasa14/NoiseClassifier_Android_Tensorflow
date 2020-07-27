package com.example.core.extensions
// package com.github.windsekirun.naraeaudiorecorder.extensions

import android.media.AudioRecord
//import com.github.windsekirun.naraeaudiorecorder.chunk.AudioChunk
import com.example.core.chunk.AudioChunk
/**
 * internal extension of check [AudioChunk] has available size
 */
internal fun AudioChunk.checkChunkAvailable() = this.getReadCount() != AudioRecord.ERROR_BAD_VALUE &&
        this.getReadCount() != AudioRecord.ERROR_INVALID_OPERATION