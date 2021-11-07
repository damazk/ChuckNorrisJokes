package com.damask.chucknorrisjokes

import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.joke_item.view.*

class JokeItem(val joke: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.jokeTv.text = joke
    }

    override fun getLayout() = R.layout.joke_item
}