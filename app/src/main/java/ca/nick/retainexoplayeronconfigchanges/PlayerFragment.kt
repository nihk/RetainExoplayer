package ca.nick.retainexoplayeronconfigchanges

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment
    : Fragment() {

    companion object {
        val TAG: String = PlayerFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_player, container, false)

    fun setExoPlayer(exoPlayer: ExoPlayer) {
        player_view.player = exoPlayer
    }
}