package com.qmp.common.vo;

import com.qmp.common.treeutil.TreeNode;
import lombok.Data;

import java.util.List;

/**
 * 描述: 测试树形工具类使用
 * 作者: 刘院民
 * 日期: 2021-10-23 14:53:30
 */
@Data
public class TestTreeUtilVO implements TreeNode<TestTreeUtilVO> {

    private Integer id;

    private String name;

    private Integer parentId;

    private List<TestTreeUtilVO> childNodes;

    @Override
    public void setChildren(List<TestTreeUtilVO> nodes) {
        this.childNodes = nodes;
    }
}
