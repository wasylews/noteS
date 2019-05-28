package com.genius.wasylews.notes.di.module;

import android.content.Context;

import com.genius.wasylews.notes.App;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        ActivityModule.class,
        FragmentModule.class

})
public interface AppModule {

    @Binds
    Context provideContext(App context);
}
