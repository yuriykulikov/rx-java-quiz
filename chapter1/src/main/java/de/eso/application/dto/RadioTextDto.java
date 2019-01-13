package de.eso.application.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters(emptyAsNulls = true)
public interface RadioTextDto {
  int id();

  int albumId();

  int artistId();

  String title();

  long duration();
}
