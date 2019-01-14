package de.eso.application;

import de.eso.api.RxPlayerApi;
import de.eso.application.dto.TunerDto;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.ext.web.client.WebClient;
import java.util.function.Consumer;

public class Main {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    HttpClient httpClient = vertx.createHttpClient();
    WebClient webClient = WebClient.wrap(httpClient);

    RxPlayerApi rxPlayerApi = new RxPlayerApiImpl(httpClient, webClient);
    Consumer<TunerDto> listener1 =
        new Consumer<TunerDto>() {
          @Override
          public void accept(TunerDto tunerDto) {
            System.out.println("listener1 callbacked with " + tunerDto);
          }
        };
    Consumer<TunerDto> listener2 =
        new Consumer<TunerDto>() {
          @Override
          public void accept(TunerDto tunerDto) {
            System.out.println("listener2 callbacked with " + tunerDto);
          }
        };
    rxPlayerApi.addTunerChangedListener(listener1);
    rxPlayerApi.addTunerChangedListener(listener2);
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
