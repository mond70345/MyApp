package com.mchen2.myapp.test.crypto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestJoin {

    @Test
    public void testJoin() throws InterruptedException {
        List<String> strs = new ArrayList<>();
        strs.add("aaa");
        strs.add("bbb");
        strs.add("ccc");
        strs.add("ddd");
        System.out.println(strs.subList(0, strs.size() / 2));
        System.out.println(strs.subList(strs.size() / 2, strs.size()));
    }
}
