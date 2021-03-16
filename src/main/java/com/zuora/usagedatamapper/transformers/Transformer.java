package com.zuora.usagedatamapper.transformers;

import org.springframework.beans.BeanUtils;

@FunctionalInterface
public interface Transformer<F, T> {
    T transform(F f);

    static <F, T> void transform(F f, T t, String... excludeFieldNames) {
        if (excludeFieldNames != null && excludeFieldNames.length > 0) {
            BeanUtils.copyProperties(f, t, excludeFieldNames);
        }
        BeanUtils.copyProperties(f, t);
    }

}
