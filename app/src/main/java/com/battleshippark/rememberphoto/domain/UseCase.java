package com.battleshippark.rememberphoto.domain;

import rx.Subscriber;

/**
 */

public interface UseCase<P, T> {
    void execute(P param, Subscriber<T> subscriber);
}
