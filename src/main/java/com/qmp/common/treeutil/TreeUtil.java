package com.qmp.common.treeutil;

import com.google.common.collect.Lists;
import com.qmp.common.beanutil.BeanHelper;

import java.util.List;
import java.util.function.Function;

/**
 * 描述: 树结构工具类
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
public class TreeUtil {


    /**
     * list转换为树
     * 
     * @param <S> 源类型
     * @param <T> 目标类型
     * @param list 源数据列表
     * @param clazz 目标数据类型
     * @param rootCondition 根节点判断
     * @param parentMatch 父级匹配
     * @return
     */
    public static <S, T extends TreeNode<T>> List<T> toTree(List<S> list, Class<T> clazz,
                                                            Function<S, Boolean> rootCondition, Whether<S> parentMatch) {
        List<T> nodes = Lists.newArrayList();
        for (S s : list) {
            if (rootCondition.apply(s)) {
                T t = BeanHelper.copyProperties(s, clazz);
                nodes.add(t);
                List<T> children = initChildren(s, clazz, list, parentMatch);
                t.setChildren(children);
            }
        }
        return nodes;
    }

    private static <S, T extends TreeNode<T>> List<T> initChildren(S s, Class<T> clazz,
            List<S> list, Whether<S> parentMatch) {
        List<T> nodes = Lists.newArrayList();
        for (S s2 : list) {
            if (parentMatch.compare(s, s2)) {
                T t = BeanHelper.copyProperties(s2, clazz);
                nodes.add(t);
                t.setChildren(initChildren(s2, clazz, list, parentMatch));
            }
        }
        return nodes;
    }

}
