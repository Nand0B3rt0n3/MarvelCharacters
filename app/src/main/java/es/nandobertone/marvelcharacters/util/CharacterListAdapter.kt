package es.nandobertone.marvelcharacters.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.nandobertone.marvelcharacters.R
import es.nandobertone.marvelcharacters.domain.model.Character
import java.security.AccessControlContext

class CharacterListAdapter(private val context:Context,var itemList:ArrayList<Character>):
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    inner class CharacterListViewHolder(view:View):RecyclerView.ViewHolder(view){
        val characterName : TextView = view.findViewById(R.id.txtCharacterName)
        val thumbnail : ImageView = view.findViewById(R.id.chImage)
        val cardCharacter : LinearLayout = view.findViewById(R.id.charactersLinearLayout)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}