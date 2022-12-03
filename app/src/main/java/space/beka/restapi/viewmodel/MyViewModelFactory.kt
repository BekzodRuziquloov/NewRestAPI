package space.beka.restapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import space.beka.restapi.reposytory.TodoRepository

class MyViewModelFactory(val todoRepository: TodoRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((TodoViewModel::class.java))){
            return TodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("Error")
    }
}