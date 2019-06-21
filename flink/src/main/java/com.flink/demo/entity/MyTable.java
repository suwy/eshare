package com.flink.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laisy
 * @date 2019/6/21
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTable {
    public String name;
    public String channel;
}