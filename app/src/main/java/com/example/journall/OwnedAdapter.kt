package com.example.journall

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class OwnedAdapter(private val jurnalList: ArrayList<Jurnal>): RecyclerView.Adapter<OwnedAdapter.MyViewHolder>() {

  private lateinit var mListener: onItemClickListener

  interface onItemClickListener {
    fun onItemClicked(position: Int)
  }

  fun setOnItemClickListener(listener: onItemClickListener) {
    mListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.delete_journal_item, parent,false)
    return OwnedAdapter.MyViewHolder(itemView, mListener)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentitem = jurnalList[position]
    holder.judul.text = currentitem.judul
    holder.penulisTahun.text = currentitem.penulis + "\n " + currentitem.tahun

    holder.deleteBtn.setOnClickListener {
      val dbref = FirebaseDatabase.getInstance().getReference("Jurnal")
      dbref.child(currentitem.key.toString()).removeValue()
      mListener.onItemClicked(position)
    }
  }

  override fun getItemCount(): Int {
    return jurnalList.size
  }


  class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
    val judul: TextView = itemView.findViewById(R.id.judul)
    val penulisTahun: TextView = itemView.findViewById(R.id.penulisTahun)
    val deleteBtn = itemView.findViewById<ImageButton>(R.id.deleteBtn)

    val intent: Intent? = null

//    init {
//      itemView.setOnClickListener {
//        listener.onItemClicked(adapterPosition)
//      }
//    }
  }
}