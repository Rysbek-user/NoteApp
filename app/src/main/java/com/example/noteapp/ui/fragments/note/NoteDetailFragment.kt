package com.example.noteapp.ui.fragments.note

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private var noteId: Int = -1
    private  var  color: Int = R.color.yellow
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tvTitle.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else if (tvTitle.text.toString().isEmpty() && tvDescription.text.toString().isEmpty()){
                        tvSave.visibility = View.GONE
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else {
                        tvSave.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else if (tvTitle.text.toString().isEmpty() && tvDescription.text.toString().isEmpty()){
                        tvSave.visibility = View.GONE
                    }
                }

            })
            tvDescription.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else if (tvTitle.text.toString().isEmpty() && tvDescription.text.toString().isEmpty()){
                        tvSave.visibility = View.GONE
                    }
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else {
                        tvSave.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                    if (tvTitle.text.toString().isNotEmpty() && tvDescription.text.toString().isNotEmpty()){
                        tvSave.visibility = View.VISIBLE
                    }else if (tvTitle.text.toString().isEmpty() && tvDescription.text.toString().isEmpty()){
                        tvSave.visibility = View.GONE
                    }
                }

            })

            ivBack.setOnClickListener{
                findNavController().navigateUp()
            }
        }



        arguments?.let{args ->
            noteId = args.getInt("noteId", -1)
        }
        if(noteId != -1){
            val id = App.appDatabase?.noteDao()?.getById(noteId)
            id?.let {item ->
                binding.tvTitle.setText(item.title)
                binding.tvDescription.setText(item.description)
                binding.tvDate.text = item.date
            }
        }
        binding.tvSave.setOnClickListener{
            if (noteId!=-1) {
                updateNote()
            }else{
                saveNote()
            }
        }
        setupListeners()
    }

    private fun saveNote() {
        val  note = NoteModel(
            title = binding.tvTitle.text.toString(),
            description = binding.tvDescription.text.toString(),
            date = getCurrentTime(),
            color = color
        )
        App.appDatabase?.noteDao()?.insertNote(note)
    }

    private fun updateNote() {
        val  note = NoteModel(
            title = binding.tvTitle.text.toString(),
            description = binding.tvDescription.text.toString(),
            date = getCurrentTime(),
            color = color
        )
        note.id = noteId
        App.appDatabase?.noteDao()?.noteUpdate(note)

    }

    private fun setupListeners() = with(binding) {
        ivImg.setOnClickListener {    Log.d("loh", "ivColor click")
            showColorDialog()}
        tvSave.setOnClickListener {
            val title = tvTitle.text.toString()
            val text = tvDescription.text.toString()
            val date = getCurrentTime()
            if (noteId != -1) {
                val updateNote = NoteModel(title, text, date, color.hashCode() )
                updateNote.id = noteId
                App.appDatabase?.noteDao()?.noteUpdate(updateNote)
            } else {
                App.appDatabase?.noteDao()?.insertNote(NoteModel(text, title, date, color.hashCode()))
            }
            findNavController().navigateUp()
        }
    }

    private fun showColorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.picked, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialogView.findViewById<View>(R.id.color_yellow).setOnClickListener {        color = R.color.yellow
            dialog.dismiss()    }
        dialogView.findViewById<View>(R.id.color_purple).setOnClickListener {        color =  R.color.purple
            dialog.dismiss()    }
        dialogView.findViewById<View>(R.id.color_pink).setOnClickListener {        color =R.color.pink
            dialog.dismiss()    }
        dialogView.findViewById<View>(R.id.color_red).setOnClickListener {        color =  R.color.red
            dialog.dismiss()    }
        dialogView.findViewById<View>(R.id.color_green).setOnClickListener {        color =  R.color.green
            dialog.dismiss()    }
        dialogView.findViewById<View>(R.id.color_blue).setOnClickListener {        color =  R.color.blue
            dialog.dismiss()    }
        dialog.show()
        val window = dialog.window
        val layoutParams = window?.attributes
        layoutParams?.gravity = Gravity.END or Gravity.TOP
        layoutParams?.x = 100
        layoutParams?.y = 100
        window?.attributes = layoutParams
    }

    private fun getCurrentTime(): String {
        val date = SimpleDateFormat("dd MMMM HH:mm")
        return date.format(Date())
    }
}