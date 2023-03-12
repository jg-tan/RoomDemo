package com.jgt.subscriberdatabase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgt.subscriberdatabase.database.Subscriber
import com.jgt.subscriberdatabase.database.SubscriberDatabase
import com.jgt.subscriberdatabase.database.SubscriberRepository
import com.jgt.subscriberdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]
        binding.subscriberViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvSubscriberList.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this) {
            Log.i("MYTAG", it.toString())
            binding.rvSubscriberList.adapter =
                SubscriberRecyclerViewAdapter(it) { selectedItem: Subscriber ->
                    listItemClicked(selectedItem)
                }
        }
    }

    private fun listItemClicked(subscriber: Subscriber) {
        Toast.makeText(this, "selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}