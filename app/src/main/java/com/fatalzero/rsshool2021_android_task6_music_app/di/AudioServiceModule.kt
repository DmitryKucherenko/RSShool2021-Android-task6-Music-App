package com.fatalzero.rsshool2021_android_task6_music_app.di

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.analytics.AnalyticsCollector
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Clock
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
object AudioServiceModule {

    @Singleton
    @Provides
    fun getExoPlayer(
        context: Context,
        dataSourceFactory: CacheDataSource.Factory
    ): SimpleExoPlayer =
        SimpleExoPlayer.Builder(
            context,
            DefaultRenderersFactory(context),
            DefaultTrackSelector(context),
            DefaultMediaSourceFactory(dataSourceFactory),
            DefaultLoadControl(),
            DefaultBandwidthMeter(),
            AnalyticsCollector(Clock.DEFAULT)
        ).build()


    @Singleton
    @Provides
    fun getDataSourceFactory(context: Context): CacheDataSource.Factory {
        val httpDataSourceFactory2: HttpDataSource.Factory =
            DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
        val evictor = LeastRecentlyUsedCacheEvictor((100 * 1024 * 1024).toLong())
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
        val cache = SimpleCache(
            File(context.cacheDir.absolutePath + "/exoplayer", "media"),
            evictor,
            databaseProvider
        )
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory2)
            .setFlags(CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    }


}
