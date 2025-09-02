package com.easy.query.core.configuration.nameconversion.impl;

import com.easy.query.core.configuration.nameconversion.NameConversion;

public class UpperSnakeCaseNameConversion implements NameConversion {
    @Override
    public String convert(String name) {
        return name
                .replaceAll("([a-z])([A-Z\\d])", "$1_$2")
                .replaceAll("([A-Z\\d]+)([A-Z][a-z]+)", "$1_$2")
                .toUpperCase();
    }
}
