package neo.mohosyny.mynote.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import neo.mohosyny.mynote.*
import neo.mohosyny.mynote.database.Note
import neo.mohosyny.mynote.view.adapter.AdapterMain
import neo.mohosyny.mynote.viewModel.NoteViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var rcvMain: RecyclerView
    private lateinit var noteAdapter: AdapterMain
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private var ID: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes()?.observe(this, Observer { list ->
            noteAdapter.submitList(list)
        })
        fab_main.setOnClickListener {
            intent = Intent(this, DetailActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)
        }

        setupRecycler()
        deleteNote()
    }

    private fun deleteNote() {
        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    Toast.makeText(this@MainActivity, "Deleted", Toast.LENGTH_SHORT).show()
                    noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.adapterPosition))
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rcvMain)

    }

    private fun setupRecycler() {
        noteAdapter = AdapterMain { position ->
            note = noteAdapter.getNoteAt(position)
            ID = note.id
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_TITLE, note.title)
            intent.putExtra(EXTRA_ID, note.id)
            intent.putExtra(EXTRA_DESC, note.description)
            intent.putExtra(EXTRA_PRIORITY, note.priority)

            startActivityForResult(intent, EDIT_REQUEST)
        }
        rcvMain = rcv_main
        rcvMain.layoutManager = LinearLayoutManager(this)
        rcvMain.setHasFixedSize(true)
        rcvMain.adapter = noteAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val title = data.getStringExtra(EXTRA_TITLE)!!
            val description = data.getStringExtra(EXTRA_DESC)!!
            val priority = data.getIntExtra(EXTRA_PRIORITY, 1)

            val note = Note(title = title, description = description, priority = priority)
            if (resultCode == Activity.RESULT_OK && requestCode == ADD_REQUEST) {
                noteViewModel.insert(note)

                Toast.makeText(this, "Note Added :)", Toast.LENGTH_SHORT)
                    .show()
            } else if (resultCode == Activity.RESULT_OK && requestCode == EDIT_REQUEST) {
                if (ID == -1) {
                    Toast.makeText(this, "Note Not updated", Toast.LENGTH_SHORT)
                        .show()
                    return
                } else {
                    note.id = ID
                    noteViewModel.update(note)
                    Toast.makeText(this, "Note  Updated :)", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> noteViewModel.deleteAllNotes()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}



