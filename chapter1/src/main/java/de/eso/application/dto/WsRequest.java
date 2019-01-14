package de.eso.application.dto;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters(emptyAsNulls = true)
public interface WsRequest {
  String id();

  boolean subscribe();

  String uri();
}