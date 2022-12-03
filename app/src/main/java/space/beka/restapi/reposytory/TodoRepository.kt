package space.beka.restapi.reposytory

import space.beka.restapi.models.MyPostTodoRequest
import space.beka.restapi.retrofit.ApiService

class TodoRepository(val apiService:ApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
    suspend fun addTodo(myPostTodoRequest: MyPostTodoRequest)=  apiService.addTodo(myPostTodoRequest)

}