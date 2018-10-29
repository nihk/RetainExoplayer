package ca.nick.retainexoplayeronconfigchanges

import android.app.Application
import android.arch.lifecycle.*
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ExoPlayerViewModel(application: Application)
    : AndroidViewModel(application)
    , LifecycleObserver {

    private val _exoPlayer = MutableLiveData<ExoPlayer>()
    val exoPlayer: LiveData<ExoPlayer> get() = _exoPlayer
    private var contentPosition = 0L
    private var playWhenReady = true

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForegrounded() {
        setUpExoPlayer()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackgrounded() {
        releaseExoPlayer()
    }

    private fun setUpExoPlayer() {
        val dataSourceFactory = DefaultDataSourceFactory(getApplication(),
            Util.getUserAgent(getApplication(), "demo"))
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse("https://html5demos.com/assets/dizzy.mp4"))

        val player = ExoPlayerFactory.newSimpleInstance(getApplication())
        player.prepare(mediaSource)
        player.playWhenReady = playWhenReady
        player.seekTo(contentPosition)

        _exoPlayer.value = player
    }

    private fun releaseExoPlayer() {
        val player = _exoPlayer.value ?: return

        contentPosition = player.contentPosition
        playWhenReady = player.playWhenReady
        player.release()
        _exoPlayer.value = null
    }

    override fun onCleared() {
        super.onCleared()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
    }
}