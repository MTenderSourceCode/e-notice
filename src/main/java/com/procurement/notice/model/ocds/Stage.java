package com.procurement.notice.model.ocds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.procurement.notice.exception.EnumException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Stage {
    EI("EI"),
    FS("FS"),
    PS("PS"),
    PQ("PQ"),
    EV("EV"),
    CT("CT");

    private static final Map<String, Stage> CONSTANTS = new HashMap<>();
    private final String value;

    static {
        for (final Stage c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    Stage(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static Stage fromValue(final String value) {
        final Stage constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new EnumException(Stage.class.getName(), value, Arrays.toString(values()));
        }
        return constant;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }
}