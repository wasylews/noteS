package com.genius.wasylews.notes.domain.base.single;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class SingleAsyncUseCase<Data> extends SingleUseCase<Data> {

    @Override
    public <T extends DisposableSingleObserver<Data>> T execute(T observer) {
        return buildTask().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
