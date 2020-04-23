package com.humanresources.assistant.backend.converters;

import java.util.List;
import java.util.function.Function;

abstract class Generic<IN, OUT> {

    protected abstract Function<IN, OUT> convertEntityToDto();

    protected abstract Function<OUT, IN> convertDtoToEntity();

    protected abstract Function<List<IN>, List<OUT>> convertListEntitiesToDto();

    protected abstract Function<List<OUT>, List<IN>> convertListDtoToEntities();

}
