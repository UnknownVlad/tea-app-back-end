package org.example.teaappbackend.aspect.mappers;

import java.util.Collection;
import java.util.Map;

public interface ValueMapper {
    Collection<Map<String, Object>> toMap(Object... objects);
}
