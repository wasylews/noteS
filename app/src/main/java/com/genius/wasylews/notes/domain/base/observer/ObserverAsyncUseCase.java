package com.genius.wasylews.notes.domain.base.observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ObserverAsyncUseCase<Data> extends ObserverUseCase<Data> {

    @Override
    public <T extends DisposableObserver<Data>> T execute(T observer) {
        return buildTask().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
