package space.beka.restapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import space.beka.restapi.databinding.ItemRvBinding
import space.beka.restapi.models.MyToDo


class TodoAllAdapter(var list: List<MyToDo> = emptyList()) : RecyclerView.Adapter<TodoAllAdapter.Vh>() {
    inner  class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(myToDo: MyToDo, position: Int) {
itemRvBinding.itemTvName.text = myToDo.sarlavha
            itemRvBinding.itemTvAbout.text = myToDo.matn
            itemRvBinding.itemTvHolati.text = myToDo.holat
            itemRvBinding.itemTvMuddat.text = myToDo.oxirgi_muddat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position] ,position)
    }

    override fun getItemCount(): Int =list.size



}

