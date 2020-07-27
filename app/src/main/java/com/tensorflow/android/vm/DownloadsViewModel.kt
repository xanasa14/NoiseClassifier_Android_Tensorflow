//package org.yausername.dvd.vm
//package com.example.spleeteryoutube3.vm
package com.tensorflow.android.vm
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
//import org.yausername.dvd.database.AppDatabase
import com.tensorflow.android.database.AppDatabase
//import org.yausername.dvd.database.Download
import com.tensorflow.android.database.Download
//import org.yausername.dvd.database.DownloadsRepository
import com.tensorflow.android.database.DownloadsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DownloadsRepository
    val allDownloads: LiveData<List<Download>>

    init {
        val downloadsDao = AppDatabase.getDatabase(application).downloadsDao()
        repository = DownloadsRepository(downloadsDao)
        allDownloads = repository.allDownloads
    }

    fun insert(word: Download) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun update(word: Download) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(word)
    }

    fun delete(word: Download) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(word)
    }
}
