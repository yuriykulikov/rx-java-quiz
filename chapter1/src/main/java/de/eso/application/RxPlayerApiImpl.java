package de.eso.application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.eso.api.RxPlayerApi;
import de.eso.application.dto.AlbumDto;
import de.eso.application.dto.ArtistDto;
import de.eso.application.dto.GsonAdaptersAlbumDto;
import de.eso.application.dto.GsonAdaptersArtistDto;
import de.eso.application.dto.GsonAdaptersRadioTextDto;
import de.eso.application.dto.GsonAdaptersStationDto;
import de.eso.application.dto.GsonAdaptersTunerDto;
import de.eso.application.dto.GsonAdaptersWsRequest;
import de.eso.application.dto.GsonAdaptersWsResponse;
import de.eso.application.dto.ImmutableWsRequest;
import de.eso.application.dto.TunerDto;
import de.eso.application.dto.WsResponse;
import io.vavr.control.Try;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class RxPlayerApiImpl implements RxPlayerApi {
  private static final String HOST = "localhost";
  private static final int PORT_WEBSOCKET = 7780;
  private static final int PORT_HTTP = 7780;

  private final CopyOnWriteArrayList<Consumer<TunerDto>> radioListeners;
  private final WebClient webClient;
  private final Gson gson;

  RxPlayerApiImpl(HttpClient httpClient, WebClient webClient) {
    Objects.requireNonNull(httpClient);
    this.webClient = Objects.requireNonNull(webClient);
    this.radioListeners = new CopyOnWriteArrayList<>();
    this.gson =
        new Gson()
            .newBuilder() //
            .registerTypeAdapterFactory(new GsonAdaptersAlbumDto())
            .registerTypeAdapterFactory(new GsonAdaptersArtistDto())
            .registerTypeAdapterFactory(new GsonAdaptersRadioTextDto())
            .registerTypeAdapterFactory(new GsonAdaptersStationDto())
            .registerTypeAdapterFactory(new GsonAdaptersTunerDto())
            .registerTypeAdapterFactory(new GsonAdaptersWsRequest())
            .registerTypeAdapterFactory(new GsonAdaptersWsResponse())
            .create();

    httpClient.websocket(
        PORT_WEBSOCKET,
        HOST,
        "/ws",
        new Handler<WebSocket>() {
          @Override
          public void handle(WebSocket websocket) {
            websocket
                .writeTextMessage(gson.toJson(ImmutableWsRequest.builder().id("666").subscribe(true).uri("/tuners").build()))
                .handler(
                    data -> {
                      Type wsResponse = new TypeToken<WsResponse<List<TunerDto>>>() {}.getType();
                      Try.of(() -> gson.<WsResponse<List<TunerDto>>>fromJson(data.toString(), wsResponse))
                          .onFailure(
                              throwable -> {
                                System.err.println("Something bad happened converting TunerDto");
                                throwable.printStackTrace();
                              })
                          .forEach(
                              tunerDtoWsResponse -> {
                                if (!tunerDtoWsResponse.payload().isEmpty()) {
                                  invokeCallbacks(tunerDtoWsResponse.payload().get(0));
                                }
                              });
                    })
                .exceptionHandler(event -> System.out.println("Something bad happend."));
          }
        },
        Throwable::printStackTrace);
  }

  @Override
  public void addTunerChangedListener(Consumer<TunerDto> callback) {
    radioListeners.add(callback);
  }

  @Override
  public void removeTunerChangedListener(Consumer<TunerDto> callback) {
    radioListeners.remove(callback);
  }

  @Override
  public void requestAlbumForId(int id, Consumer<AlbumDto> callback) {
    HttpRequest<Buffer> bufferHttpRequest = webClient.get(PORT_HTTP, HOST, "/albums/" + id);
    request(() -> bufferHttpRequest, gson, AlbumDto.class, callback);
  }

  @Override
  public void requestArtistForId(int id, Consumer<ArtistDto> callback) {
    HttpRequest<Buffer> bufferHttpRequest = webClient.get(PORT_HTTP, HOST, "/artists/" + id);
    request(() -> bufferHttpRequest, gson, ArtistDto.class, callback);
  }

  private void invokeCallbacks(TunerDto dto) {
    radioListeners.forEach(
        tunerDataConsumer -> {
          tunerDataConsumer.accept(dto);
        });
  }

  private static <T> void request(Supplier<HttpRequest<Buffer>> request, Gson gson, Class<T> dto, Consumer<T> callback) {
    request
        .get()
        .as(
            BodyCodec.create(
                buffer -> {
                  return gson.fromJson(buffer.toString(), dto);
                }))
        .send(
            event -> {
              AsyncResult<T> result = event.map(HttpResponse::body);
              if (result.succeeded()) {
                callback.accept(result.result());
              } else {
                throw new RuntimeException(result.cause());
              }
            });
  }
}
