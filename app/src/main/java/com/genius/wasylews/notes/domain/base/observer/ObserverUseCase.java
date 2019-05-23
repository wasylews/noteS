package com.genius.wasylews.notes.domain.base.observer;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public abstract class ObserverUseCase<Data> {

    protected abstract Observable<Data> buildTask();

    public <T extends DisposableObserver<Data>> T execute(final T observer) {
        return buildTask().subscribeWith(observer);
    }
}
