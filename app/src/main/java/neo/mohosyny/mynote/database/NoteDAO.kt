package neo.mohosyny.mynote.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query ("SELECT * FROM note_tbl Order By priority ")
    fun getAllNotes(): LiveData<List<Note>>

    @Query ("Delete From note_tbl")
    fun deleteAllNotes()

}