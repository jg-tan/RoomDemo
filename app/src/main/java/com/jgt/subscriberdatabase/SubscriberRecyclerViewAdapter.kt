package com.jgt.subscriberdatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jgt.subscriberdatabase.database.Subscriber
import com.jgt.subscriberdatabase.databinding.SubscriberListItemBinding

class SubscriberRecyclerViewAdapter(
    private val subscribers: List<Subscriber>,
    private val clickListener: (Subscriber) -> Unit
) : RecyclerView.Adapter<SubscriberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SubscriberListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.subscriber_list_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        val subscriber = subscribers[position]
        holder.bind(subscriber, clickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }
}

class SubscriberViewHolder(private val binding: SubscriberListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.tvSubscriberName.text = subscriber.name
        binding.tvSubscriberEmail.text = subscriber.email
        binding.cvSubscriber.setOnClickListener {
            clickListener(subscriber)
        }
    }
}