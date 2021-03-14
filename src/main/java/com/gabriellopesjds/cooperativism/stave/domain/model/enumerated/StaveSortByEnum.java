package com.gabriellopesjds.cooperativism.stave.domain.model.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum StaveSortByEnum {

    THEME("theme"),
    DESCRIPTION("description");
    private final String value;

    private static final Map<String, StaveSortByEnum> notificationRecipientSortByEnumMap =
        new HashMap<>();

    static {
        notificationRecipientSortByEnumMap.put(THEME.name(), THEME);
        notificationRecipientSortByEnumMap.put(DESCRIPTION.name(), DESCRIPTION);
    }

    public static StaveSortByEnum fromValueOrDefault(String value) {
        return notificationRecipientSortByEnumMap.getOrDefault(value, THEME);
    }
}
