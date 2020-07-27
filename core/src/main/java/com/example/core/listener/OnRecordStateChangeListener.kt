//package com.github.windsekirun.naraeaudiorecorder.listener
package com.example.core.listener
//import com.github.windsekirun.naraeaudiorecorder.model.RecordState
import com.example.core.model.RecordState
/**
 * Listener for handling state changes
 */
interface OnRecordStateChangeListener {

    /**
     * Call when [RecordState] is changed
     */
    fun onState(state: RecordState)
}