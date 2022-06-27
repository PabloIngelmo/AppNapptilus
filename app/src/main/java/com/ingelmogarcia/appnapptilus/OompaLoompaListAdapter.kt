package com.ingelmogarcia.appnapptilus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaItemModel

class OompaLoompaListAdapter(
    private val listOompaLoompas: List<OompaLoompaItemModel>,
    private val listener: (OompaLoompaItemModel) -> Unit
) : RecyclerView.Adapter<OompaLoompaListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cover_oompa_loompa, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOompaLoompas[position])
        holder.itemView.setOnClickListener { listener(listOompaLoompas[position]) }
    }

    override fun getItemCount(): Int = listOompaLoompas.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.image)
        private val title = view.findViewById<TextView>(R.id.title)

        fun bind(oompaLoompaModel: OompaLoompaItemModel) {
            Glide
                .with(image.context)
                .load(oompaLoompaModel.urlImage)
                .into(image)
            this.title.text = oompaLoompaModel.title
        }
    }
}