package es.nandobertone.marvelcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.nandobertone.marvelcharacters.databinding.ActivityMainBinding
import es.nandobertone.marvelcharacters.domain.model.Character
import es.nandobertone.marvelcharacters.ui.CharactersList.CharactersViewModel
import es.nandobertone.marvelcharacters.util.CharacterListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var flag = 3
    var paginatedValue = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : CharacterListAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val viewModel: CharactersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.CharactersRecyclerView
        layoutManager = GridLayoutManager(this, 2)
        recyclerViewCharacters()
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition()==layoutManager.itemCount-1){
                    paginatedValue += 20
                    viewModel.getAllCharactersData(paginatedValue)
                    callApi()
                }
            }
        })
    }

    private fun callApi() {
        CoroutineScope(Dispatchers.Main).launch{
            repeat(flag){
                viewModel._marvelValue.collect{
                    when{
                        it.isLoading->{

                        }
                        it.error.isNotBlank()->{

                        }
                        it.characterList.isNotEmpty()->{
                            binding.progressBar.visibility = View.GONE
                            flag = 0
                            adapter.setData(it.characterList as ArrayList<Character>)
                        }
                    }
                }

            }
        }
    }

    private fun recyclerViewCharacters() {
        adapter = CharacterListAdapter(this, ArrayList())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }
}