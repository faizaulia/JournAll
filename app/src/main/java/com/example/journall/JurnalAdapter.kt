package com.example.journall

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class JurnalAdapter(private val jurnalList: ArrayList<Jurnal>): RecyclerView.Adapter<JurnalAdapter.MyViewHolder>() {

  private lateinit var mListener: onItemClickListener

  interface onItemClickListener {
    fun onItemClicked(position: Int)
  }

  fun setOnItemClickListener(listener: onItemClickListener) {
    mListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cari_journal_item, parent,false)
    return JurnalAdapter.MyViewHolder(itemView, mListener)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentitem = jurnalList[position]
    holder.judul.text = currentitem.judul
    holder.penulisTahun.text = currentitem.penulis + " " + currentitem.tahun
  }

  override fun getItemCount(): Int {
    return jurnalList.size
  }


  class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
    val judul: TextView = itemView.findViewById(R.id.judul)
    val penulisTahun: TextView = itemView.findViewById(R.id.penulisTahun)

    val intent: Intent? = null

    init {
      itemView.setOnClickListener {
        Log.d("TAG", "TOLONG ISI ${itemView}")

//        listener.onItemClicked(adapterPosition)
      }
    }
  }
}
