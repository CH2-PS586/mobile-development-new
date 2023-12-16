//package com.dicoding.elanhakim.storyApp.view.detail
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
//import com.dicoding.elanhakim.storyApp.databinding.ItemFilesBinding
//
//class ListMusicAdapter: ListAdapter<MusicResponseItem, ListMusicAdapter.ViewHolder>(DIFF_CALLBACK){
//
//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    inner class ViewHolder(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(files: MusicResponseItem){
//            val imgFiles = binding.ivFile
//            binding.tvNama.text = files.filename
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val (name, photo) = listFolder[position]
//        holder.imgFolder.setImageResource(photo)
//        holder.tvName.text = name
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