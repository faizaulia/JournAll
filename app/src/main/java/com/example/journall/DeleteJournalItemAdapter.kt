package com.example.journall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class DeleteJournalItemAdapter(val jurnal: LiveData<Array<String>>) : RecyclerView.Adapter<DeleteJournalItemAdapter.JurnalViewHolder>() {
    inner class JurnalViewHolder(items : View) : RecyclerView.ViewHolder(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JurnalViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.delete_journal_item, parent, false)
        return JurnalViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: JurnalViewHolder, position: Int) {
        holder.itemView.apply{
            val jurnalName = findViewById<TextView>(R.id.judul)
            val jurnalPenulisTahun = findViewById<TextView>(R.id.penulisTahun)
            val deleteBtn = findViewById<ImageButton>(R.id.deleteBtn)

            jurnalName.text = jurnal.value?.get(0) ?: "nama"
            jurnalPenulisTahun.text = jurnal.value?.get(1) ?: "nama"
            deleteBtn.setOnClickListener {
                //TODO : Hapus item di on click listener
            }
        }
    }

    override fun getItemCount(): Int {
        return jurnal.value?.size ?: 1
    }
}