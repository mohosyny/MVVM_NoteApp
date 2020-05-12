package neo.mohosyny.mynote.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import neo.mohosyny.mynote.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        number_picker.maxValue = 10
        number_picker.minValue = 1

        getIntentExtra()

        btn_save.setOnClickListener {
           saveNote()
        }
    }

    private fun getIntentExtra() {
        val intent=intent
        if (intent.hasExtra(EXTRA_ID)) {
            btn_save.text=getString(R.string.update)
            edt_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edt_desc.setText(intent.getStringExtra(EXTRA_DESC))
            number_picker.value = intent.getIntExtra(EXTRA_PRIORITY, 1)

        }else btn_save.text=getString(R.string.save)
    }

    private fun saveNote() {
        val title: String = edt_title.text.toString()
        val desc: String = edt_desc.text.toString()
        val priority = number_picker.value

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(
                this,
                "please fill all fields",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESC, desc)
        data.putExtra(EXTRA_PRIORITY, priority)


        val id: Int = intent.getIntExtra(EXTRA_ID, 1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()

    }


}
