package neo.mohosyny.mynote.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import neo.mohosyny.mynote.database.Note
import neo.mohosyny.mynote.database.NoteDAO
import neo.mohosyny.mynote.database.NoteDataBase

class NoteRepository(application: Application) : ViewModel() {
    private var dao: NoteDAO? = null
    private var allNote: LiveData<List<Note>>? = null

    init {
        val database = NoteDataBase.getInstance(application)
        dao = database.dao
        allNote = dao!!.getAllNotes()
    }


    fun insert(note: Note): AsyncTask<Note, Boolean, Boolean> = InsertAsync(dao!!).execute(note)
    fun delete(note: Note): AsyncTask<Note, Boolean, Boolean> = DeleteAsync(dao!!).execute(note)
    fun deleteAll(): AsyncTask<Void, Boolean, Boolean> = DeleteAllAsync(dao!!).execute()
    fun update(note: Note): AsyncTask<Note, Boolean, Boolean> = UpdateAsync(dao!!).execute(note)

    fun getAll(): LiveData<List<Note>> {
        return allNote!!
    }


    class InsertAsync(private val dao: NoteDAO) : AsyncTask<Note, Boolean, Boolean>() {

        override fun doInBackground(vararg params: Note): Boolean {
            dao.insertNote(params[0])
            return true
        }
    }

    class DeleteAsync(private val dao: NoteDAO) : AsyncTask<Note, Boolean, Boolean>() {

        override fun doInBackground(vararg params: Note): Boolean {
            dao.deleteNote(params[0])
            return true
        }
    }

    class UpdateAsync(private val noteDao: NoteDAO) : AsyncTask<Note, Boolean, Boolean>() {

        override fun doInBackground(vararg params: Note?): Boolean {
            noteDao.updateNote(params[0]!!)
            return true
        }

    }


    class DeleteAllAsync(private val dao: NoteDAO) : AsyncTask<Void, Boolean, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean {
            dao.deleteAllNotes()
            return true
        }
    }


}