package de.eso.api;

import de.eso.application.dto.AlbumDto;
import de.eso.application.dto.ArtistDto;
import de.eso.application.dto.TunerDto;
import de.eso.rxplayer.Album;
import de.eso.rxplayer.Artist;
import java.util.function.Consumer;

public interface RxPlayerApi {
  /**
   * Register for change-notification (async), when {@link TunerDto} changes
   *
   * <p>The current {@link TunerDto} will be pushed to the listener on listener-registration
   *
   * <p>{@link TunerDto} is regarded 'changed', when one of the immediate fields has changed.
   *
   * <p>Changed-Example: TunerDto#radioText/ TunerDto#stationIndex changes its value -> this will be regarded as 'changed'.
   *
   * <p>Changed-Example: TunerDto#radioText#albumId changes its value -> this will NOT be regarded as 'changed'
   *
   * <p>NOTE: Callback will be called never or infinite times.
   */
  void addTunerChangedListener(Consumer<TunerDto> callback);

  /**
   * Remove registered listener
   *
   * <p>NOTE: Equality is done be comparing references
   */
  void removeTunerChangedListener(Consumer<TunerDto> callback);

  /**
   * Request information for a {@link Album} with given {@code id}
   *
   * <p>NOTE: Callback will be called async
   *
   * <p>NOTE: Given {@code callback} will be called once or never
   */
  void requestAlbumForId(int id, Consumer<AlbumDto> callback);

  /**
   * Request information for a {@link Artist} with given {@code id}
   *
   * <p>NOTE: Callback will be called async
   *
   * <p>NOTE: Given {@code callback} will be called once or never
   */
  void requestArtistForId(int id, Consumer<ArtistDto> callback);
}
