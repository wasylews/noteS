package com.genius.wasylews.notes.domain.base.completable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableAsyncUseCase extends CompletableUseCase {

    @Override
    public <T extends DisposableCompletableObserver> T execute(T observer) {
        return buildTask().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
