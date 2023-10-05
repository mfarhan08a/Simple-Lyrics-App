package com.mfarhan08a.simplelyricsapp.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfarhan08a.simplelyricsapp.core.R
import com.mfarhan08a.simplelyricsapp.core.databinding.ItemListTrackBinding
import com.mfarhan08a.simplelyricsapp.core.domain.model.Track

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.ListViewHolder>() {
    private var listData = ArrayList<Track>()
    var onItemClick: ((Track) -> Unit)? = null

    fun setData(newListData: List<Track>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_track, parent, false)
        )


    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListTrackBinding.bind(itemView)
        fun bind(data: Track) {
            with(binding) {
                tvItemName.text = data.trackName
                tvItemArtist.text = data.artistName
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }


}