package com.dkgtech.notefirebasemvvm.screen.MainActivity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkgtech.notefirebasemvvm.R
import com.dkgtech.notefirebasemvvm.adapter.RecyclerNoteAdapter
import com.dkgtech.notefirebasemvvm.databinding.ActivityMainBinding
import com.dkgtech.notefirebasemvvm.databinding.NoteDialogBinding
import com.dkgtech.notefirebasemvvm.model.NoteModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var firebaseDB: FirebaseFirestore

    lateinit var auth: FirebaseAuth

    lateinit var collectionReference: CollectionReference

    companion object {
        val TAG = "MainActivity"
    }

    val arrNotes = ArrayList<NoteModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDB = Firebase.firestore
        auth = Firebase.auth

        collectionReference = firebaseDB
            .collection("users")
            .document(auth.currentUser!!.uid)
            .collection("notes")

        with(binding) {

            btnFB.setOnClickListener {
                val dialog = Dialog(this@MainActivity)
                val dialogBinding = NoteDialogBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)

                with(dialogBinding) {

                    btnAddNote.setOnClickListener {

                        val title = edtTitle.text.toString()
                        val desc = edtDesc.text.toString()

                        if (title != "" && desc != "") {

                            val noteModel = NoteModel(title, desc)

                            collectionReference
                                .add(noteModel)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Note Added Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    dialog.dismiss()
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "Failure Error : ${it.message}")
                                    it.printStackTrace()
                                }
                        }
                    }

                }

                dialog.show()
            }


// read data

            collectionReference
                .get()
                .addOnSuccessListener {
                    for (doc in it.documents) {

                        val noteModel = doc.toObject(NoteModel::class.java)
                        noteModel?.let {
                            noteModel.docId = doc.id
                            arrNotes.add(noteModel)

                        }
                    }

                    //            recycler view setup

                    rcViewNotes.layoutManager = LinearLayoutManager(this@MainActivity)
                    rcViewNotes.adapter = RecyclerNoteAdapter(this@MainActivity, arrNotes)
                }


        }

    }
}