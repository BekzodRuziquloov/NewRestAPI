package space.beka.restapi

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import space.beka.restapi.adapter.TodoAllAdapter
import space.beka.restapi.databinding.ActivityMainBinding
import space.beka.restapi.databinding.ItemDialogBinding
import space.beka.restapi.models.MyPostTodoRequest
import space.beka.restapi.reposytory.TodoRepository
import space.beka.restapi.retrofit.ApiClient
import space.beka.restapi.retrofit.ApiService
import space.beka.restapi.utils.Status
import space.beka.restapi.viewmodel.MyViewModelFactory
import space.beka.restapi.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoAllAdapter: TodoAllAdapter
    private lateinit var todoRepository: TodoRepository
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        todoRepository = TodoRepository(ApiClient.getApiService())
        todoViewModel = ViewModelProvider(this , MyViewModelFactory(todoRepository)).get(TodoViewModel::class.java)
        todoAllAdapter = TodoAllAdapter()
        binding.rv.adapter = todoAllAdapter
        todoViewModel.getAllTodo()
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d(TAG, "onCreate: Loading")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "onCreate: Error ${it.massage}")
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Error ${it.massage}", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCESS -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        todoAllAdapter.list = it?.data!!
                        todoAllAdapter.notifyDataSetChanged()
                        binding.progressBar.visibility = View.GONE

                    }
                }

            }

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialog.setView(itemDialogBinding.root)
itemDialogBinding.apply {
    btnSave.setOnClickListener{

        val myPostTodoRequest= MyPostTodoRequest(
            spinnerStatus.selectedItem.toString(),
            edtAbout.text.toString().trim(),
            edtDeadline.text.toString(),
            edtTitle.text.toString().trim()
        )
        todoViewModel.addMyTodo(myPostTodoRequest).observe(this@MainActivity){
            when(it.status){
                Status.LOADING->{
progressPost.visibility = View.VISIBLE
                    linerDialog.isEnabled =false
                }
                Status.ERROR->{
                    Toast.makeText(this@MainActivity, "Xatolik , ${it.massage}", Toast.LENGTH_SHORT).show()

                    progressPost.visibility = View.GONE
                    linerDialog.isEnabled =true
                }
                Status.SUCCESS->{
                    Toast.makeText(this@MainActivity, "${it.data?.sarlavha} ${it.data?.id} ga saqlandi", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }
            }
        }
    }
}

            dialog.show()
        }
    }

}