package co.hillstech.a99vidas

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import co.hillstech.a99vidas.retrofit.WebService
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_games.view.*
import java.text.DecimalFormat
import kotlin.Unit



class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var game = Session.game!!

        var ac = supportActionBar
        ac?.title = game.name
        ac?.setDisplayHomeAsUpEnabled(true)

        txtName.text = game.name
        txtAno.text = game.year.toString()
        txtEpisode.text = "#${game.episode}"
        txtSynopsis.text = game.synopsis

        var num = DecimalFormat("#00")

        if(game.rating_99vidas != null) txt99Vidas.text = "${num.format(game.rating_99vidas)}"
        else txt99Vidas.text = "--"

        if(game.rating_community != null) txtCommunity.text = "${num.format(game.rating_community)}"
        else txtCommunity.text = "--"

        if(game.rating_quantum != null) txtQuantum.text = "${num.format(game.rating_quantum)}"
        else txtQuantum.text = "--"

        Glide.with(this)
                .load(game.image)
                .into(imgCover)

        Glide.with(this)
                .load(BuildConfig.HTTP_SERVER+"v1/getConsole/"+game.console!!.replace("/","")+".png")
                .into(imgConsole)

        Glide.with(this)
                .load(game.image)
                .into(imgBackground)

        startBar(game.user_rating)
    }

    fun startBar(pos: Int?){
        // Kotlin
        val max = 99
        val min = 0
        val total = max - min

        val slider = flsNote
        slider.positionListener = { pos -> slider.bubbleText = "${min + (total  * pos).toInt()}" }

        if(pos == null){
            slider.position = 0.0f
        }else{
            var aux = (pos+1)*0.01f
            slider.position = aux
        }

        slider.startText ="$min"
        slider.endText = "$max"

        slider.endTrackingListener = {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Você já jogou?")
            dialogBuilder.setMessage("Essa informação é importante para marcarmos o seu voto como um voto real ou um voto quantico. Se você nunca jogou esse game, selecione \"Voto Quantico\".")
            dialogBuilder.setPositiveButton("Voto Real", DialogInterface.OnClickListener { dialog, whichButton ->
                setVote(Integer.parseInt(slider.bubbleText),1)
            })
            dialogBuilder.setNegativeButton("Voto Quantico", DialogInterface.OnClickListener { dialog, whichButton ->
                setVote(Integer.parseInt(slider.bubbleText),2)
            })
            dialogBuilder.show()
            Unit
        }

        slider.forceLayout()

    }

    fun setVote(vote: Int, type: Int){
        val webService = WebService.create()

        webService.setVote(Session.userId!!,vote,Session.game!!.id!!,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            //on-success
                            Toast.makeText(this,"Voto computado com sucesso!",Toast.LENGTH_SHORT).show()
                            finish()
                        },
                        {
                            //on-failure
                            Toast.makeText(this,"Erro ao computar voto...",Toast.LENGTH_SHORT).show()
                        }
                )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
