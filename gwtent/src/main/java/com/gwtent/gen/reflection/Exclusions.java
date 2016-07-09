package com.gwtent.gen.reflection;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.dev.util.collect.HashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Exclusions {

    private final Set<String> exactPatterns;
    private final Set<String> wildcardPatterns;

    public Exclusions(Iterable<String> exclusionPatterns) {
            exactPatterns = new HashSet<String>();
            wildcardPatterns = new HashSet<String>();
        for (String pattern : exclusionPatterns) {
            if (pattern.endsWith(".*")) {
                wildcardPatterns.add(pattern.substring(0, pattern.length() - 2));
            } else {
                exactPatterns.add(pattern);
            }
        }
    }

    public static Exclusions empty() {
        return new Exclusions(new ArrayList<String>());
    }

    public static Exclusions fromPatterns(String... patterns) {
        return new Exclusions(Arrays.asList(patterns));
    }

    public static Exclusions fromConfigurationProperty(GeneratorContext context, String propertyName) {
        List<String> exclusionPatters = new ArrayList<String>();
        try {
            ConfigurationProperty property = context.getPropertyOracle().getConfigurationProperty(propertyName);
            if (property.getValues() != null) {
                for (String value : property.getValues()) {
                    if (value.contains(",")) {
                        for (String token : value.split(",")) {
                            exclusionPatters.add(token.trim());
                        }
                    } else {
                        exclusionPatters.add(value);
                    }

                }
            }
        } catch (BadPropertyValueException e) {
        }

        return new Exclusions(exclusionPatters);
    }

    public boolean shouldExclude(String typeQualifiedName) {
        if (exactPatterns.contains(typeQualifiedName)) {
            return true;
        } else {
            for (String wildcardStart : wildcardPatterns) {
                if (typeQualifiedName.startsWith(wildcardStart)) {
                    return true;
                }
            }
        }

        return false;
    }
}
