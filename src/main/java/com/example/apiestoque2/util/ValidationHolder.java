package com.example.apiestoque2.util;

public class ValidationHolder<T> {

    public Class<T> clazz;
    public T holder;

    public ValidationHolder(Class<T> clazz, T holder) {
        this.clazz = clazz;
        this.holder = holder;
    }

    public static class ValidationHolderException extends RuntimeException {
        public ValidationHolderException(String message) {
            super(message);
        }
    }

//    public T apply() {
//        Map<String, Predicate<Object>> predicates = ValidationExtractor.extractValidationPredicates(clazz);
//
//        for (Predicate<T> predicate : predicates) {
//            if (!predicate.test(holder)) {
//                throw new ValidationHolderException("Validation failed!");
//            }
//        }
//
//        return holder;
//    }
}
