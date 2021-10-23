package com.qmp.common.beanutil;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * 描述: MapperFactory持有者
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
public interface BeanMapperFactoryHolder {

    static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    default MapperFactory getMapperFactory() {
        return MAPPER_FACTORY;
    }
}
