# RetainExoplayer
This repo is code to accompany my Medium article:
https://medium.com/@nicholas.rose/leveraging-aac-to-manage-exoplayer-state-a9c0a19d6c14

The official ExoPlayer uses an activity with a manifest attribute looking like:
android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"

This is effectively a hack and undesireable for several reasons I will not go into here. This project shows an
example of how to retain an ExoPlayer instance across configuration changes without resorting to this hack while still
maintaining its lifecycle when the app is backgrounded/foregrounded.

TL;DR Use a ViewModel composed of the ExoPlayer instance, and have that ViewModel listen to ProcessLifecycleOwner
changes to manage ExoPlayer state.
