package com.qmp.common.test;

import com.google.common.collect.Lists;
import com.qmp.common.jsonutil.JsonUtils;
import com.qmp.common.treeutil.TreeUtil;
import com.qmp.common.vo.TestTreeUtilVO;

import java.util.List;
import java.util.Objects;

/**
 * 描述: 测试类
 * 作者: 刘院民
 * 日期: 2021-10-23 14:54:44
 */
public class TreeUtilTest {

    public static void main(String[] args) {
        List<TestTreeUtilVO> testTreeUtilVOS = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
            TestTreeUtilVO testTreeUtilVO = new TestTreeUtilVO();
            testTreeUtilVO.setId(i);
            testTreeUtilVO.setName("属性名称"+i);
            if(i == 1){
                testTreeUtilVO.setParentId(0);
            } else if (i != 0){
                testTreeUtilVO.setParentId(1);
            }
            testTreeUtilVOS.add(testTreeUtilVO);
        }
        System.out.println("testTreeUtilVOS = " + testTreeUtilVOS);
        List<TestTreeUtilVO> tree = TreeUtil.toTree(testTreeUtilVOS, TestTreeUtilVO.class,
                item -> Objects.equals(item.getId(), 0),
                (i1, i2) -> i1.getId().equals(i2.getParentId()));
        System.out.println("tree = " + JsonUtils.toString(tree));
    }
}
