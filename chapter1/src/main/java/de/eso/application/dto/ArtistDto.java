package de.eso.application.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters(emptyAsNulls = true)
public interface ArtistDto {
  int id();

  String name();
}
