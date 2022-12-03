package space.beka.restapi.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import space.beka.restapi.models.MyPostTodoRequest
import space.beka.restapi.models.MyPostTodoResponse
import space.beka.restapi.models.MyToDo

interface ApiService {
    @GET("plan")
    suspend fun getAllTodo():List<MyToDo>
@POST("plan")
suspend fun  addTodo(@Body myPostTodoRequest: MyPostTodoRequest):MyPostTodoResponse
}