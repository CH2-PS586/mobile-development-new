package com.dicoding.elanhakim.storyApp.view.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.storyApp.databinding.ItemFilesBinding

class MusicAdapter : RecyclerView.Adapter<MusicItemView>(){

    private val list = ArrayList<MusicResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MusicItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    fun setList(music: List<MusicResponseItem>){
        list.addAll(music)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

}

//class MusicAdapter: ListAdapter<MusicResponseItem, MusicAdapter.ViewHolder>(DIFF_CALLBACK){
//
//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    inner class ViewHolder(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(music: MusicResponseItem){
//            binding.tvNama.text = music.filename
//            binding.tvDate.text = music.updatedAt
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//
//    }
//
//    companion object{
//        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MusicResponseItem>(){
//            override fun areItemsTheSame(oldItem: MusicResponseItem, newItem: MusicResponseItem): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(oldItem: MusicResponseItem, newItem: MusicResponseItem): Boolean {
//                return oldItem == newItem
//            }
//
//        }
//    }
//
//    interface OnItemClickCallback{
//        fun onItemClicked(items : MusicResponseItem)
//    }
//}