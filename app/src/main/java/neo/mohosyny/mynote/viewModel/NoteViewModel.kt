package neo.mohosyny.mynote.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import neo.mohosyny.mynote.database.Note
import neo.mohosyny.mynote.repository.NoteRepository

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository = NoteRepository(application)
    private var allNotes: LiveData<List<Note>>? = null

    init {
       allNotes = noteRepository.getAll()
    }

    fun insert(note: Note) {
       noteRepository.insert(note)
    }

    fun update(note: Note) {
       noteRepository.update(note)
    }

    fun delete(note: Note) {
       noteRepository.delete(note)
    }

    fun deleteAllNotes() {
       noteRepository.deleteAll()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
       return allNotes!!
    }
}