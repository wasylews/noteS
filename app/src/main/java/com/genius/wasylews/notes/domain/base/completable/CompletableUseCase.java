package com.genius.wasylews.notes.domain.base.completable;

import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;

public abstract class CompletableUseCase {

    protected abstract Completable buildTask();

    public <T extends DisposableCompletableObserver> T execute(final T observer) {
        return buildTask().subscribeWith(observer);
    }
}
