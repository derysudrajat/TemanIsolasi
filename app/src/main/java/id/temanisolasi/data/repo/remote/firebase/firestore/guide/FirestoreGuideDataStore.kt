package id.temanisolasi.data.repo.remote.firebase.firestore.guide

import id.temanisolasi.data.model.ContentGuide
import id.temanisolasi.data.repo.State
import kotlinx.coroutines.flow.Flow

interface FirestoreGuideDataStore {

    fun getFirstIsolation(): Flow<State<List<ContentGuide>>>
    fun getInIsolation(): Flow<State<List<ContentGuide>>>
    fun getLastIsolation(): Flow<State<List<ContentGuide>>>
}