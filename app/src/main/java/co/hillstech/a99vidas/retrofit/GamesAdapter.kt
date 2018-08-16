package co.hillstech.a99vidas.retrofit

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.hillstech.a99vidas.R

class GamesAdapter(private val games: MutableList<Game>,
                    private val context: Context) : RecyclerView.Adapter<GamesHolder>() {

    override fun onBindViewHolder(holder: GamesHolder, position: Int) {
        val game = games[position]

        holder?.let{
            it.bindView(game)
        }
    }

    fun remove(position: Int) {
        games.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_games, parent, false)
        return GamesHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

}