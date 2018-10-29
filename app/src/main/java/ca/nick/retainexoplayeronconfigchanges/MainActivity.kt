package ca.nick.retainexoplayeronconfigchanges

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity
    : AppCompatActivity() {

    private lateinit var viewModel: ExoPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = supportFragmentManager.findFragmentByTag(PlayerFragment.TAG)
                as? PlayerFragment

        if (fragment == null) {
            fragment = PlayerFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, PlayerFragment.TAG)
                .commit()
        }

        viewModel = ViewModelProviders.of(this).get(ExoPlayerViewModel::class.java)
        viewModel.exoPlayer.observe(this, Observer { exoPlayer ->
            exoPlayer?.let {
                fragment.setExoPlayer(it)
            }
        })
    }
}
