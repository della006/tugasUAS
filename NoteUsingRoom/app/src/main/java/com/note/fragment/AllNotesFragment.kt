package com.note.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.note.activity.DetailNoteActivity
import com.note.adapter.NoteAdapter
import com.note.databinding.FragmentAllNotesBinding
import com.note.entity.Note
import com.note.viewModel.NotesViewModel

class AllNotesFragment : Fragment() {
    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listNoteAdapter: NoteAdapter
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
        initListener()

    }

    private fun initView() {

        binding.rvAll.setHasFixedSize(true)
        listNoteAdapter = NoteAdapter()
        listNoteAdapter.notifyDataSetChanged()

        binding.rvAll.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvAll.adapter = listNoteAdapter

        listNoteAdapter.setOnClicked(object : NoteAdapter.NoteListener {
            override fun onItemClicked(note: Note) {
                val i = Intent(context, DetailNoteActivity::class.java)
                i.putExtra(DetailNoteActivity().editNoteExtra, note)
                startActivity(i)
            }
        })
    }

    private fun initViewModel() {
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        notesViewModel.getNotes().observe(viewLifecycleOwner, Observer { notes ->

            if (notes.isNotEmpty()) {
                binding.textViewNoteEmpty.visibility = View.GONE
            } else {
                binding.textViewNoteEmpty.visibility = View.VISIBLE
            }
            listNoteAdapter.setData(notes)
        })
    }

    private fun initListener(){
        notesViewModel.setNotes()
    }
//
//    override fun onResume() {
//        super.onResume()
//
//        //update list
//        initListener()
//    }
}