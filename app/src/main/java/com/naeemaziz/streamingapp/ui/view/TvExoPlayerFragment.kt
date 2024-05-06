package com.naeemaziz.streamingapp.ui.view

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.naeemaziz.streamingapp.R
import com.naeemaziz.streamingapp.data.model.TvLink
import com.naeemaziz.streamingapp.data.viewmodel.ExoPlayerViewModel


class TvExoPlayerFragment: Fragment() , Player.Listener{


    private val viewModel: ExoPlayerViewModel by activityViewModels()

    var mplayerView: StyledPlayerView? = null
    var mplayer: ExoPlayer? = null
    var simpleProgressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val mview =  inflater.inflate(R.layout.fragment_exoplayer, container, false)
        mplayerView = mview.findViewById(R.id.player_view);
        simpleProgressBar =  mview.findViewById(R.id.progressBar);
        return mview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tvLink.observe(viewLifecycleOwner) { tvLink ->
            tvLink?.let {
                if(tvLink.Type.toString().equals("flash"))
                    startFlashPlayerVideo(tvLink)
                else
                    startSimplePlayer(tvLink.Url)
            }
        }
    }

    fun startSimplePlayer(videourl: String) {

        mplayer = ExoPlayer.Builder(requireActivity()).build()
        mplayerView?.setPlayer(mplayer)
        val videouri = Uri.parse(videourl)
        val mediaItem: MediaItem = MediaItem.fromUri(videouri)
        mplayer?.setMediaItem(mediaItem)
        mplayer?.addListener(this)
        mplayer?.prepare()
        mplayer?.play()
    }

    fun startFlashPlayerVideo(tvLink: TvLink) {

            val uriOfContentUrl = Uri.parse(tvLink.Url)
            var mUserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59"
            mplayer = ExoPlayer.Builder(requireActivity()).build()
            mplayerView!!.player = mplayer
            val httpDataSourceFactory: HttpDataSource.Factory =
                DefaultHttpDataSource.Factory().setUserAgent(mUserAgent)
                    .setAllowCrossProtocolRedirects(true)
            val dataSourceFactory: DataSource.Factory = DataSource.Factory {
                val dataSource = httpDataSourceFactory.createDataSource()
                dataSource.setRequestProperty("Origin", tvLink.Origin.toString())
                dataSource.setRequestProperty("Referer", tvLink.Referer.toString())
                dataSource.setRequestProperty(":authority", tvLink.Authority.toString())
                dataSource.setRequestProperty("Accept", "*/*")
                dataSource.setRequestProperty("Accept-Encoding", "gzip, deflate, br")
                dataSource.setRequestProperty("Accept-Language", "en-US,en;q=0.9")
                dataSource.setRequestProperty("Sec-Fetch-Dest", "empty")
                dataSource.setRequestProperty("sec-ch-ua-platform", "macOS")
                dataSource.setRequestProperty("Sec-Fetch-Mode", "cors")
                dataSource.setRequestProperty("Sec-Fetch-Site", "cross-site")
                dataSource.setRequestProperty("Cache-Control", "no-cache")
                dataSource.setRequestProperty("Pragma", "no-cache")
                dataSource.setRequestProperty("Connection", "keep-alive")
                if(tvLink.Host !=null && tvLink.Host.toString().length > 5){
                    dataSource.setRequestProperty("Host", tvLink.Host.toString())
                }
                dataSource
            }
            val mediaSource: MediaSource =
                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                    MediaItem.fromUri(uriOfContentUrl)
                )
            mplayer!!.addListener(this)
            mplayer!!.prepare(mediaSource)
            mplayer!!.play()

    }

    override  fun onPlaybackStateChanged(@Player.State playbackState: Int) {
        if (playbackState == Player.STATE_BUFFERING) {
            simpleProgressBar?.setVisibility(View.VISIBLE)
        } else if (playbackState == Player.STATE_READY) {
            simpleProgressBar?.setVisibility(View.GONE)
        } else if (playbackState == Player.STATE_ENDED) {
            simpleProgressBar?.setVisibility(View.GONE)
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        simpleProgressBar?.setVisibility(View.GONE)
        mplayer?.release()
        Toast.makeText(requireActivity(),
            "Please wait & try again!",
            Toast.LENGTH_SHORT
        ).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mplayer?.release()
    }



}