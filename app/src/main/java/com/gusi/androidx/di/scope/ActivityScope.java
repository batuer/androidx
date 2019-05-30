package com.gusi.androidx.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author ylw  2018/6/21 14:24
 */
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
