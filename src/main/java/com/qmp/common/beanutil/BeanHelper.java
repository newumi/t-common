package com.qmp.common.beanutil;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cglib.core.ReflectUtils;

import java.util.List;
import java.util.Set;

/**
 * 描述: java bean对象值互相copy
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
@Slf4j
public class BeanHelper {

    private static BeanMapperFactoryHolder beanMapperFactoryHolder;

    private static final String STATIC_BEAN_BINDER_PATH = "com.qmp.aop.util.bean.BeanMapperBinder";

    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            return getMapperFacade().map(source, target);
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new RuntimeException(String.format("【数据转换】数据转换出错，目标对象%s构造函数异常",target.getName()));
        }
    }

    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target) {
        try {
            return getMapperFacade().mapAsList(sourceList, target);
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new RuntimeException(String.format("【数据转换】数据转换出错，目标对象%s构造函数异常",target.getName()));
        }
    }

    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target) {
        try {
            return getMapperFacade().mapAsSet(sourceList, target);
        } catch (Exception e) {
            log.error("【数据转换】数据转换出错，目标对象{}构造函数异常", target.getName(), e);
            throw new RuntimeException(String.format("【数据转换】数据转换出错，目标对象%s构造函数异常",target.getName()));
        }
    }

    public static MapperFacade getMapperFacade() {
        if (beanMapperFactoryHolder == null) {
            synchronized (BeanHelper.class) {
                if (beanMapperFactoryHolder == null) {
                    initBeanMapperFactoryHolder();
                }
            }
        }
        return beanMapperFactoryHolder.getMapperFactory().getMapperFacade();
    }

    private static void initBeanMapperFactoryHolder() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> beanBinderClass = classLoader.loadClass(STATIC_BEAN_BINDER_PATH);
            beanMapperFactoryHolder =
                    (BeanMapperFactoryHolder) ReflectUtils.newInstance(beanBinderClass);
        } catch (Exception e) {
            // 当前环境没有引入 t-qmp-base-aop-utils模块,使用默认实现
            beanMapperFactoryHolder = new BeanMapperFactoryHolder() {};
        }
    }
}
