package rx.quiz.combining;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.reactivex.Observable.defer;
import static io.reactivex.Observable.timer;

public class ProTimerKoanTest extends ProTimerKoan {

    @NotNull
    @Override
    public Observable<String> sayWordsOfTheSentenceWithPausesBetweenThem(
            @NotNull Scheduler scheduler, @NotNull String phrase, @NotNull List<Long> pauses) {
        return defer(() -> {
            String[] words = phrase.split(" ");

            List<Observable<String>> observables = IntStream.range(0, words.length)
                    .mapToObj(index ->
                            defer(() -> timer(pauses.get(index), TimeUnit.SECONDS, scheduler).map(l -> words[index])))
                    .collect(Collectors.toList());

            return Observable.concat(observables);
        });
    }
}
