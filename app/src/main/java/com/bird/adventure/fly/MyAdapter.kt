package com.bird.adventure.fly

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bird.adventure.fly.databinding.ListItemBinding

class MyAdapter(context: Context): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    val data = context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getStringSet("records",HashSet<String>())
        ?.map { it.toInt() }?.toList()?.sortedBy { -it }.orEmpty()


    class MyHolder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        private var binding = binding

        public fun bind(f: String) {
            binding.textView6.text = f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind("${position+1}. "+data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}