package com.example.mymovie.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.adapters.NowPlayingAdapter
import com.example.mymovie.adapters.PopularMovieAdapter
import com.example.mymovie.api.vo.NowPlayingVO
import com.example.mymovie.api.vo.PopularMovieVO
import com.example.mymovie.databinding.ActivityMainBinding
import com.example.mymovie.databinding.NavHeaderBinding
import com.example.mymovie.delegates.MovieItemDelegate
import com.example.mymovie.local.DatabaseProvider
import com.example.mymovie.viewModel.MovieViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import java.net.URI.create

class MainActivity : BaseActivity(), MovieItemDelegate {

    private lateinit var binding: ActivityMainBinding

    private lateinit var popularAdapter: PopularMovieAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private lateinit var auth: FirebaseAuth

    private val mViewModel: MovieViewModel by viewModels()

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        auth = Firebase.auth

//if not CurrentUser change to Login
        if (auth.currentUser == null) {
            toLoginActivity()
            finish()
        }

        binding.apply {

//navigation bar set up
            val toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, toolbar, 0, 0)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            val nav = NavigationView.OnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_bookmark -> {
                        navBookmark()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.nav_logout -> {
                        accountLogout()
                    }
                }
                false
            }

            navView.setNavigationItemSelectedListener(nav)
            val headerView = navView.getHeaderView(0)
            val headerViewBinding = NavHeaderBinding.bind(headerView)
            val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)

            if (account!=null){
                 headerViewBinding.tvUserName.text = account.displayName
                headerViewBinding.tvUserMail.text = account.email

                Glide.with(this@MainActivity)
                    .load(account.photoUrl)
                    .into(headerViewBinding.ivUser)


            }
            else{
                headerViewBinding.tvUserName.text = auth.currentUser?.displayName
                headerViewBinding.tvUserMail.text = auth.currentUser?.email
            }

        }
        mViewModel.getMovie()
//Popular ViewModel observe
        mViewModel.popularVM.observe(this) {
            popularAdapter(it.popularMovieVO as MutableList<PopularMovieVO>)

            binding.apply {
//                val list = mutableListOf<CarouselItem>()
//                list.add(
//                    CarouselItem(
//                        imageUrl ="https://image.tmdb.org/t/p/original"+it.popularMovieVO[0].backdrop_path,
//                        caption = ""
//
//                    )
//                )
                val top5Movie = it.popularMovieVO.slice(0..4)
                var item: MutableList<CarouselItem> = mutableListOf()

                for (i in top5Movie.indices) {
                    item.add(
                        CarouselItem(
                            imageUrl = "https://image.tmdb.org/t/p/original" + it.popularMovieVO[i].backdrop_path,
                            caption = ""
                        )
                    )
                }
                imageCarousel.addData(item)

            }

        }
//NowPlaying ViewModel observe
        mViewModel.nowPlayingVM.observe(this) {
            nowPlayingAdapter(it.nowPlayingVO as MutableList<NowPlayingVO>)
        }

    }
//Account Log out
    private fun accountLogout(){
    val dialog = AlertDialog.Builder(this@MainActivity)
        .setTitle("Logout")
        .setMessage("Are you sure to Log out")
        .setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        .setPositiveButton("Yes") { dialog, _ ->
            logout()
            finishAffinity()
            dialog.dismiss()
        }.create()
    dialog.show()
    }

//NowPlaying Adapter
    private fun nowPlayingAdapter(it: MutableList<NowPlayingVO>) {
        nowPlayingAdapter = NowPlayingAdapter(this, it)

        binding.rvNowPlaying.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowPlaying.adapter = nowPlayingAdapter
    }

//Popular Adapter
    private fun popularAdapter(it: MutableList<PopularMovieVO>) {
        popularAdapter = PopularMovieAdapter(this, it)

        binding.rvPopularMovie.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularMovie.adapter = popularAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onTapPopularItem(popularMovieVO: PopularMovieVO) {
        val intent = MovieDetailActivity2.intent(applicationContext)
        intent.putExtra("popularMovie", popularMovieVO)
        startActivity(intent)
    }

    override fun onTapNowPlayingItem(nowPlayingVO: NowPlayingVO) {
        val intent = MovieDetailActivity2.intent(applicationContext)
        intent.putExtra("nowPlaying", nowPlayingVO)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}

