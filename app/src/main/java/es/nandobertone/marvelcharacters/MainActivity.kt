package es.nandobertone.marvelcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.nandobertone.marvelcharacters.databinding.ActivityMainBinding
import es.nandobertone.marvelcharacters.domain.model.Character
import es.nandobertone.marvelcharacters.ui.charactersList.CharactersViewModel
import es.nandobertone.marvelcharacters.util.CharacterListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var searchTerm : String
    private lateinit var binding: ActivityMainBinding
    private var flag = 3
    var paginatedValue = 0
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : CharacterListAdapter
    private lateinit var layoutManager: GridLayoutManager
    private val viewModel : CharactersViewModel by viewModels()
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
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        it.error.isNotBlank()->{
                            binding.progressBar.visibility = View.GONE
                            flag = 0
                            Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_SHORT).show()
                        }
                        it.characterList.isNotEmpty()->{
                            binding.progressBar.visibility = View.GONE
                            flag = 0
                            adapter.setData(it.characterList as ArrayList<Character>)
                        }
                    }
                    delay(1000)
                }

            }
        } //
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val search = menu.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    private fun recyclerViewCharacters() {
        adapter = CharacterListAdapter(this, ArrayList())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchTerm = query
        }
        if (searchTerm.isNotEmpty()){
            search()
            }
            return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null){
            searchTerm = newText
        }
        if (searchTerm.isNotEmpty()){
            search()
        }
        return true
    }

    private fun search() {
        viewModel.getSearchedCharacters(searchTerm)
        CoroutineScope(Dispatchers.Main).launch{
            viewModel._marvelValue.collect{
                when{
                    it.isLoading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    it.error.isNotBlank()->{
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_SHORT).show()
                    }
                    it.characterList.isNotEmpty()->{
                        binding.progressBar.visibility = View.GONE
                        adapter.setData(it.characterList as ArrayList<Character>)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCharactersData(paginatedValue)
        callApi()
    }
}