package com.naeemaziz.streamingapp.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView
import com.naeemaziz.streamingapp.R
import com.naeemaziz.streamingapp.data.model.TvLink


class ListAdapter(private var tvLinks: List<TvLink>,
                  private val onItemClick: (TvLink) -> Unit) : RecyclerView.Adapter<ListAdapter.TvLinkViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvLinkViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_row, parent, false)
        return TvLinkViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tvLinks.size
    }

    override fun onBindViewHolder(holder: TvLinkViewHolder, position: Int) {
        val tvLink = tvLinks[position]
        holder.bind(tvLink,holder)
    }
    fun setData(newTvLinks: List<TvLink>) {
        tvLinks = newTvLinks
        notifyDataSetChanged()
    }

    inner class TvLinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = itemView.findViewById(R.id.textName)
        private val titleTextView: TextView = itemView.findViewById(R.id.texttitle)
        private val typeTextView: TextView = itemView.findViewById(R.id.textType1)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textdescription)
        private val mimageView: RoundedImageView = itemView.findViewById(R.id.imageTV)

        fun bind(tvLink: TvLink, holder: TvLinkViewHolder) {

            nameTextView.text = tvLink.Name
            titleTextView.text = tvLink.Title
            if( !tvLink.Type.toString().equals("Mp4")){
                typeTextView.text = tvLink.Type + " : Live"
            }else{
                typeTextView.text = tvLink.Type
            }

            descriptionTextView.text = tvLink.Description

            itemView.setOnClickListener { onItemClick(tvLink) }

            val with: RequestManager = Glide.with(holder.itemView.context)
            val options: RequestOptions = RequestOptions()
                .fitCenter()
                .placeholder(com.naeemaziz.streamingapp.R.drawable.ic_launcher_background)
                .error(com.naeemaziz.streamingapp.R.drawable.ic_launcher_background)
            (with.load(tvLink.ImageUrl) as RequestBuilder<*>).apply(options).into(mimageView)

        }
    }
}