package com.example.journall

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class JurnalAdapter(private val jurnalList: ArrayList<Jurnal>): RecyclerView.Adapter<JurnalAdapter.MyViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cari_journal_item, parent,false)
    return JurnalAdapter.MyViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentitem = jurnalList[position]
    holder.judul.text = currentitem.judul
    holder.penulisTahun.text = currentitem.penulis + " " + currentitem.tahun
  }

  override fun getItemCount(): Int {
    return jurnalList.size
  }

  class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val judul: TextView = itemView.findViewById(R.id.judul)
    val penulisTahun: TextView = itemView.findViewById(R.id.penulisTahun)

    val intent: Intent? = null

    init {
      itemView.setOnClickListener {
        Log.d("TAG", "Item ke ${adapterPosition}")
//        intent = Intent(this@MyViewHolder, InputJurnalActivity::class.java)
//        ContextCompat.startActivity(intent)
      }
    }
  }
}
