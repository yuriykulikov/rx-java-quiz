package de.eso.application;

import de.eso.api.RxPlayerApi;
import de.eso.application.dto.TunerDto;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.WebClient;
import java.util.function.Consumer;

public class Main extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(Main.class);
  }

  @Override
  public void start(Future<Void> startFuture) {
    WebClient webClient = WebClient.create(vertx);
    HttpClient httpClient = this.vertx.createHttpClient();

    RxPlayerApi rxPlayerApi = new RxPlayerApiImpl(httpClient, webClient);

    Consumer<TunerDto> listener1 =
        new Consumer<TunerDto>() {
          @Override
          public void accept(TunerDto tunerDto) {
            System.out.println("listener callbacked with " + tunerDto);
          }
        };
    Consumer<TunerDto> listener2 =
        new Consumer<TunerDto>() {
          @Override
          public void accept(TunerDto tunerDto) {
            System.out.println("listener callbacked with " + tunerDto);
          }
        };
    rxPlayerApi.addTunerChangedListener(listener1);
    // rxPlayerApi.addTunerChangedListener(listener2);
    rxPlayerApi.requestAlbumForId(
        1,
        albumDto -> {
          System.out.println("get data " + albumDto);
        });
    rxPlayerApi.requestArtistForId(
        1,
        artistDto -> {
          System.out.println("get data" + artistDto);
        });
  }
}
