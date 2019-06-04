import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import rx.quiz.basics.ProStatefulScanKoan;

public class StatefulScan extends ProStatefulScanKoan {
    @NotNull
    @Override
    protected ObservableTransformer<Character, Result> createStringTokenizer() {
        return new ObservableTransformer<Character, Result>() {
            @Override
            public ObservableSource<Result> apply(Observable<Character> upstream) {
                AtomicInteger numberVal = new AtomicInteger(-1);
                final String[] s = {""};
                PublishSubject<Result> subject = PublishSubject.create();
                upstream.subscribe(value -> {
                    if(numberVal.get() == -1) {
                        try {
                            numberVal.set(Integer.parseInt(String.valueOf(value)));
                        } catch (NumberFormatException nfe) {
                            subject.onError(new IllegalArgumentException());
                        }
                    } else if(numberVal.get() == 0) {
                        subject.onNext(new Result(s[0].length(), s[0]));
                        s[0] = "";
                        try {
                            numberVal.set(Integer.parseInt(String.valueOf(value)));
                        } catch (NumberFormatException nfe) {
                            subject.onError(new IllegalArgumentException());
                        }
                    } else {
                        numberVal.decrementAndGet();
                        s[0] = s[0] + value;
                    }
                });
                return Observable.create(emitter -> {
                    subject.subscribe(value -> emitter.onNext(value));
                });
            }
        };
    }
}
