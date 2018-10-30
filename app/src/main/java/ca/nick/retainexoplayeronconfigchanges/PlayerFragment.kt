package ca.nick.retainexoplayeronconfigchanges

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment
    : Fragment() {

    private lateinit var viewModel: PlayerViewModel

    companion object {
        val TAG: String = PlayerFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_player, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)
        viewModel.player.observe(this, Observer {
            player_view.player = it
        })
    }
}