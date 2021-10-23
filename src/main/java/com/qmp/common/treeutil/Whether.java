package com.qmp.common.treeutil;

@FunctionalInterface
public interface Whether<T> {

    boolean compare(T t1,T t2);
}
