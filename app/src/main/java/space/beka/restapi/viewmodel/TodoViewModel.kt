package space.beka.restapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import space.beka.restapi.models.MyPostTodoRequest
import space.beka.restapi.models.MyPostTodoResponse
import space.beka.restapi.models.MyToDo
import space.beka.restapi.reposytory.TodoRepository
import space.beka.restapi.retrofit.ApiClient
import space.beka.restapi.retrofit.ApiService
import space.beka.restapi.utils.Resources

class TodoViewModel (val todoRepository: TodoRepository): ViewModel() {

    private val liveData = MutableLiveData<Resources<List<MyToDo>>>()
//    private val apiService = ApiClient.getApiService()

    fun getAllTodo(): MutableLiveData<Resources<List<MyToDo>>> {
        viewModelScope.launch {
            liveData.postValue(Resources.loading("loading"))
            try {
                coroutineScope {
                    val list = async {
                        todoRepository.getAllTodo()
                    }.await()

                    liveData.postValue(Resources.success(list))
                    todoRepository.getAllTodo()
                }
            } catch (e: Exception) {
                liveData.postValue(Resources.error(e.message))
            }
        }
        return liveData
    }
private val postLiveData =MutableLiveData<Resources<MyPostTodoResponse>>()
    fun addMyTodo(myPostTodoRequest: MyPostTodoRequest):MutableLiveData<Resources<MyPostTodoResponse>> {
        viewModelScope.launch {
            postLiveData.postValue(Resources.loading("Loading post "))
            try {

                coroutineScope {
                    val response =    async {

                        todoRepository.addTodo(myPostTodoRequest)
                    }.await()
                    postLiveData.postValue(Resources.success(response))
                    getAllTodo()
                }
            } catch (e: Exception) {
postLiveData.postValue(Resources.error(e.message))
            }
        }
        return postLiveData
    }
}