package co.hillstech.a99vidas.retrofit

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import co.hillstech.a99vidas.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_games.view.*
import java.text.DecimalFormat

class GamesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var num = DecimalFormat("#00")

    fun bindView(game: Game) {

        if(game.rating_99vidas != null) itemView.txt99Vidas.text = "${num.format(game.rating_99vidas)}"
        else itemView.txt99Vidas.text = "--"

        if(game.rating_community != null) itemView.txtCommunity.text = "${num.format(game.rating_community)}"
        else itemView.txtCommunity.text = "--"

        if(game.rating_quantum != null) itemView.txtQuantum.text = "${num.format(game.rating_quantum)}"
        else itemView.txtQuantum.text = "--"

        itemView.txtGameName.text = "${game.name}"

        itemView.setOnClickListener {
            Session.game = game
            val intent = Intent(itemView.getContext(), DetailsActivity::class.java)
            itemView.getContext().startActivity(intent)
        }

        Glide.with(itemView)
                .load(game.image)
                .into(itemView.imgCover)



    }
}