package neo.mohosyny.mynote.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {

    abstract val dao: NoteDAO

    companion object {
        private var INSTANCE: NoteDataBase? = null
        private const val DB_NAME = "note_DB"

        fun getInstance(context: Context): NoteDataBase {

            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDataBase::class.java, DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(callback)
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return INSTANCE!!
        }


        private var callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateAsyncTask(INSTANCE!!).execute()
            }
        }


    }

    class PopulateAsyncTask(db: NoteDataBase) :
        AsyncTask<Void, Boolean, Boolean>() {
        private var dao = db.dao

        override fun doInBackground(vararg params: Void?): Boolean {
            dao.insertNote(Note(title = "Title 1", description = "This is Description 1", priority = 1, id = 0))
            dao.insertNote(Note(title = "Title 2", description = "This is Description 2", priority = 2, id = 0))
            dao.insertNote(Note(title = "Title 3", description = "This is Description 3", priority = 3, id = 0))
            dao.insertNote(Note(title = "Title 4", description = "This is Description 4", priority = 4, id = 0))
            dao.insertNote(Note(title = "Title 5", description = "This is Description 5", priority = 5, id = 0))


            return true
        }
    }
}