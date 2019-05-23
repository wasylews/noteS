package com.genius.wasylews.notes.domain.base.single;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleUseCase<Data> {

    protected abstract Single<Data> buildTask();

    public <T extends DisposableSingleObserver<Data>> T execute(final T observer) {
        return buildTask()
                .subscribeWith(observer);
    }
}
