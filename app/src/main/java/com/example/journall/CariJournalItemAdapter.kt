package com.example.journall

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView

class CariJournalItemAdapter(val jurnal: LiveData<Array<String>>) : RecyclerView.Adapter<CariJournalItemAdapter.JurnalViewHolder>() {
    inner class JurnalViewHolder(items : View) : RecyclerView.ViewHolder(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JurnalViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.cari_journal_item, parent, false)
        return JurnalViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: JurnalViewHolder, position: Int) {
        holder.itemView.apply{
            val jurnalName = findViewById<TextView>(R.id.judul)
            val jurnalPenulisTahun = findViewById<TextView>(R.id.penulisTahun)
            val detailBtn = findViewById<ImageButton>(R.id.detailBtn)

            jurnalName.text = jurnal.value?.get(0) ?: "nama"
            jurnalPenulisTahun.text = jurnal.value?.get(1) ?: "nama"
            detailBtn.setOnClickListener {
                val intent = Intent(context, DetailJurnalActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return jurnal.value?.size ?: 1
    }
}