// Copyright (C) 2015 anduo
// All rights reserved
package xyz.anduo.jvm.ch10;

import java.io.Serializable;

/**
 * Summary: 用来测试的类
 * Author : anduo@qq.com
 * Version: 1.0
 * Date   : 15/5/24
 * time   : 09:33
 */
public class Student implements Serializable,Comparable{

    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";

    private Long id;
    private String name;
    private String no;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override public int compareTo(Object o) {
        return 0;
    }
}
