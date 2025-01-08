package com.joshgm3z.bingo.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {
    @Provides
    fun providesCoroutineScope() = CoroutineScope(Dispatchers.IO)

    @Provides
    fun bindsContext(@ApplicationContext context: Context) = context
}