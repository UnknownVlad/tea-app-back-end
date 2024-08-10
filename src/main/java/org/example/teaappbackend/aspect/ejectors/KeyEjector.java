package org.example.teaappbackend.aspect.ejectors;

import java.util.Map;

public interface KeyEjector {
    Map<String, Object> eject(Object... objects);
}
