package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ProCreateCombineLatestKoanTest extends ProCreateCombineLatestKoan {

    @NotNull
    @Override
    public <T, D, I> Observable<I> combineLatest(@NotNull Observable<T> first, @NotNull Observable<D> second,
                                                 @NotNull BiFunction<T, D, I> combiner) {
        return Observable.create(emitter -> {
            CompositeDisposable compositeDisposable = new CompositeDisposable();

            AtomicInteger countActive = new AtomicInteger(2);
            AtomicReference<T> firstLatest = new AtomicReference<>(null);
            AtomicReference<D> secondLatest = new AtomicReference<>(null);

            Action completed = () -> {
                if (countActive.decrementAndGet() < 1) {
                    emitter.onComplete();
                }
            };

            Disposable firstDisposable = first.subscribe(firstLatest::set, emitter::onError, completed);
            Disposable secondDisposable = second.subscribe(secondLatest::set, emitter::onError, completed);

            Disposable disposable = Observable.merge(first, second)
                    .filter(o -> firstLatest.get() != null && secondLatest.get() != null)
                    .forEach(o -> emitter.onNext(combiner.apply(firstLatest.get(), secondLatest.get())));

            compositeDisposable.addAll(disposable, firstDisposable, secondDisposable);

            emitter.setDisposable(compositeDisposable);
        });
    }
}
