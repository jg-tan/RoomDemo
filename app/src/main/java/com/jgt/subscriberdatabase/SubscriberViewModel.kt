package com.jgt.subscriberdatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgt.subscriberdatabase.database.Subscriber
import com.jgt.subscriberdatabase.database.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private var subscriberSelected = Subscriber(0, "", "")

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButton = MutableLiveData<String>()
    val clearAllOrDeleteButton = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButton.value = "Save"
        clearAllOrDeleteButton.value = "Clear All"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberSelected.name = inputName.value!!
            subscriberSelected.email = inputEmail.value!!
            update(subscriberSelected)
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
        }

    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberSelected)
        } else {
            clearAll()
        }
    }

    private fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.insert(subscriber)
        resetFieldsAndButton()
        withContext(Main) {
            statusMessage.value = Event("Subscriber inserted successfully")
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.update(subscriber)
        resetFieldsAndButton()
        withContext(Main) {
            statusMessage.value = Event("Subscriber updated successfully")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch(IO) {
        repository.delete(subscriber)
        resetFieldsAndButton()
        withContext(Main) {
            statusMessage.value = Event("Subscriber deleted successfully")
        }
    }

    private fun clearAll() = viewModelScope.launch(IO) {
        repository.deleteAll()
        withContext(Main) {
            statusMessage.value = Event("Subscribers cleared successfully")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        subscriberSelected = subscriber
        toggleUpdateOrDelete(true)
    }

    private fun toggleUpdateOrDelete(toUpdateOrDelete: Boolean) {
        if (toUpdateOrDelete) {
            isUpdateOrDelete = true
            saveOrUpdateButton.value = "Update"
            clearAllOrDeleteButton.value = "Delete"
        } else {
            isUpdateOrDelete = false
            saveOrUpdateButton.value = "Save"
            clearAllOrDeleteButton.value = "Clear ALl"
        }
    }

    private suspend fun resetFieldsAndButton() {
        withContext(Dispatchers.Main) {
            inputName.value = ""
            inputEmail.value = ""
            toggleUpdateOrDelete(false)
        }
    }
}
