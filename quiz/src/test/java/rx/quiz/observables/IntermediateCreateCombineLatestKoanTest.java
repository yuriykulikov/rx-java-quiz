package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class IntermediateCreateCombineLatestKoanTest extends IntermediateCreateCombineLatestKoan {

    @NotNull
    @Override
    public <T, D, I> Observable<I> combineLatest(@NotNull Observable<T> first, @NotNull Observable<D> second,
                                                 @NotNull BiFunction<T, D, I> combiner) {
        return Observable.create(emitter -> {
            CompositeDisposable compositeDisposable = new CompositeDisposable();

            AtomicReference<T> firstLatest = new AtomicReference<>(null);
            AtomicReference<D> secondLatest = new AtomicReference<>(null);

            Disposable firstDisposable = first.subscribe(firstLatest::set);
            Disposable secondDisposable = second.subscribe(secondLatest::set);

            Disposable disposable = Observable.merge(first, second)
                    .filter(o -> firstLatest.get() != null && secondLatest.get() != null)
                    .forEach(o -> emitter.onNext(combiner.apply(firstLatest.get(), secondLatest.get())));

            compositeDisposable.addAll(disposable, firstDisposable, secondDisposable);

            emitter.setDisposable(compositeDisposable);
        });
    }
}
