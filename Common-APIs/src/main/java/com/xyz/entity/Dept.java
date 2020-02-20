package com.xyz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xyz
 * @data    2020年2月20日
 * @decription 部门实体类
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Dept {

    private int deptNo;
    private String  deptName;
    private String  deptDesc;

}
