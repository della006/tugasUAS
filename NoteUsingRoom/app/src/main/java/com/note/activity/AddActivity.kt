package com.note.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.awesomedialog.*
import com.note.R
import com.note.databinding.ActivityAddBinding
import com.note.entity.Note
import com.note.method.DateChange
import com.note.viewModel.NotesViewModel


class AddActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddBinding
    val editNoteExtra = "edit_note_extra"
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var note: Note
    private val dateChange = DateChange()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewModel()
        initListener()

    }

    private fun initView() {

        if (intent.getParcelableExtra<Note>(editNoteExtra) != null) {

            note = intent.getParcelableExtra(editNoteExtra)!!
            binding.editTextTitle.setText(note.title)
            binding.editTextBody.setText(note.body)
            binding.editTextTitle.setSelection(note.title.length)

            //set spinner position
            val compareValue = note.label
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.labels,
                android.R.layout.simple_spinner_item
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spLabel.adapter = adapter

            val spinnerPosition = adapter.getPosition(compareValue)
            binding.spLabel.setSelection(spinnerPosition)
        }
    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }


    private fun initListener() {
        binding.toolbar.nibBack.setOnClickListener(this)
        binding.toolbar.btnSave.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.nib_back -> {
                onBackPressed()
            }
            R.id.btn_save -> {
                val title = binding.editTextTitle.text.toString()
                val body = binding.editTextBody.text.toString()
                val label = binding.spLabel.selectedItem.toString()
                val date = dateChange.getToday()
                val time = dateChange.getTime()

                if (title.isEmpty() && body.isEmpty()) {
                    Toast.makeText(this@AddActivity, "Note tidak bisa kosong", Toast.LENGTH_SHORT)
                        .show()
                } else {
//                    if (isUpdate) {
                    notesViewModel.insertNote(
                        Note(
                            title = title,
                            label = label,
                            date = date,
                            time = time,
                            body = body
                        )
                    )
                    Toast.makeText(this@AddActivity, "Note berhasil ditambah", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                    }
                }
            }
        }
    }
