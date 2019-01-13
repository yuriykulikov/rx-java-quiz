package de.eso.application.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters(emptyAsNulls = true)
public interface TunerDto {
  StationDto station();

  RadioTextDto radioText();

  int stationIndex();
}
