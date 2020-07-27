//package org.yausername.dvd.database
//package com.example.spleeteryoutube3.database
package com.tensorflow.android.database

import androidx.lifecycle.LiveData

class DownloadsRepository(private val downloadsDao: DownloadsDao) {

    val allDownloads: LiveData<List<Download>> = downloadsDao.getAllDownloads()

    suspend fun insert(download: Download) {
        downloadsDao.insert(download)
    }

    suspend fun update(download: Download) {
        downloadsDao.update(download)
    }

    suspend fun delete(download: Download) {
        downloadsDao.delete(download)
    }
}

