package ca.nick.retainexoplayeronconfigchanges

import android.app.Application
import android.arch.lifecycle.*
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class PlayerViewModel(application: Application)
    : AndroidViewModel(application)
    , LifecycleObserver {

    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player?> get() = _player
    private var contentPosition = 0L
    private var playWhenReady = true

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegrounded() {
        setUpPlayer()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgrounded() {
        releaseExoPlayer()
    }

    private fun setUpPlayer() {
        val dataSourceFactory = DefaultDataSourceFactory(
            getApplication(),
            Util.getUserAgent(getApplication(), "demo")
        )
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse("https://html5demos.com/assets/dizzy.mp4"))

        val player = ExoPlayerFactory.newSimpleInstance(getApplication())
        player.prepare(mediaSource)
        player.playWhenReady = playWhenReady
        player.seekTo(contentPosition)

        this._player.value = player
    }

    private fun releaseExoPlayer() {
        val player = _player.value ?: return
        this._player.value = null

        contentPosition = player.contentPosition
        playWhenReady = player.playWhenReady
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        releaseExoPlayer()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}