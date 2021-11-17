package com.example.journall

import android.app.Activity
import android.app.Application
import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class JurnalAdapter(private val jurnalList: ArrayList<Jurnal>): RecyclerView.Adapter<JurnalAdapter.MyViewHolder>() {

  private lateinit var mListener: onItemClickListener

  interface onItemClickListener {
    fun onItemClicked(key: String?, position: Int)
  }

  fun setOnItemClickListener(listener: onItemClickListener) {
    mListener = listener
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cari_journal_item, parent,false)
    return MyViewHolder(itemView, mListener)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentitem = jurnalList[position]
    holder.itemView.apply {
      findViewById<TextView>(R.id.judul).text = currentitem.judul
      findViewById<TextView>(R.id.penulisTahun).text = currentitem.penulis + "\n " + currentitem.tahun
//      findViewById<ImageButton>(R.id.detailBtn).setima (R.drawable.ic_baseline_arrow_forward_24)
    }
//    holder.judul.setOnClickListener {
//      val intent = Intent(this@JurnalList, Login::class.java)
//      startActivity(intent)
//    }
//    holder.judul.text = currentitem.judul
//    holder.penulisTahun.text = currentitem.penulis + "\n " + currentitem.tahun
  }

  override fun getItemCount(): Int {
    return jurnalList.size
  }


  inner class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
  {
    val judul: TextView = itemView.findViewById(R.id.judul)
    val penulisTahun: TextView = itemView.findViewById(R.id.penulisTahun)

    val intent: Intent? = null

    init {
      itemView.setOnClickListener {
        mListener.onItemClicked(jurnalList[adapterPosition].key, adapterPosition)
      }
    }
  }
}
