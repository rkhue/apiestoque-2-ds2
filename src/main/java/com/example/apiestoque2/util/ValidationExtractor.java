package com.example.apiestoque2.util;

import jakarta.validation.constraints.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ValidationExtractor {

    public static <T> List<Map<String, Predicate<Object>>> extractValidationPredicates(Class<T> clazz) {
        List<Map<String, Predicate<Object>>> validations = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            for (Annotation annotation : field.getAnnotations()) {

                if (annotation instanceof NotNull) {
                    Map<String, Predicate<Object>> validation = new HashMap<>();
                    validation.put(fieldName, instance -> {
                        try {
                            return field.get(instance) != null;
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    validations.add(validation);
                }

                if (annotation instanceof Size size) {
                    Map<String, Predicate<Object>> validation = new HashMap<>();
                    validation.put(fieldName, instance -> {
                        try {
                            Object value = field.get(instance);
                            if (value == null) return true;
                            int length = (value instanceof String) ? ((String) value).length()
                                    : (value instanceof List) ? ((List<?>) value).size()
                                    : -1;
                            return length >= size.min() && length <= size.max();
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    validations.add(validation);
                }

                if (annotation instanceof Pattern pattern) {
                    Map<String, Predicate<Object>> validation = new HashMap<>();
                    validation.put(fieldName, instance -> {
                        try {
                            Object value = field.get(instance);
                            if (value == null) return true;
                            return value.toString().matches(pattern.regexp());
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    validations.add(validation);
                }

                // Add more constraints here...
            }
        }

        return validations;
    }
}
