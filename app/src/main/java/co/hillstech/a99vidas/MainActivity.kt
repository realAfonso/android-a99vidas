package co.hillstech.a99vidas

import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import co.hillstech.a99vidas.retrofit.Game
import co.hillstech.a99vidas.retrofit.GamesAdapter
import co.hillstech.a99vidas.retrofit.WebService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer_layout.useCustomBehavior(Gravity.START)
        drawer_layout.useCustomBehavior(Gravity.END)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.right_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item_notifications -> {
                drawer_layout.openDrawer(Gravity.END)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getGames()

    }

    fun getGames(){
        val webService = WebService.create()

        webService.getGames(Session.userId!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            //on-success

                            Handler().postDelayed({
                                inflateGameList(result)
                                try{pbPacman.hide()}catch (e: Exception){}
                            }, 2000)

                        },
                        {
                            //on-failure
                        }
                )
    }

    fun inflateGameList(games: MutableList<Game>){

        var state: Parcelable? = null

        try{state = rcvGames.layoutManager.onSaveInstanceState()}catch (e: Exception){}

        //rcvGames.addItemDecoration(SimpleDividerItemDecoration(getApplicationContext()))
        rcvGames.adapter = GamesAdapter(games, this)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvGames.layoutManager = layoutManager

        /*var swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                //to-do
            }
        })

        val itemTouchhelper = ItemTouchHelper(swipeController)
        itemTouchhelper.attachToRecyclerView(rcvGames)

        rcvGames.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
                swipeController.onDraw(c)
            }
        })*/

        if(state != null) rcvGames.layoutManager.onRestoreInstanceState(state)
    }

}
