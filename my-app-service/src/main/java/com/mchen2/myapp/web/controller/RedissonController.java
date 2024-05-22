package com.mchen2.myapp.web.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.google.common.collect.Maps;
import com.mchen2.myapp.utils.RedissonUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@RequestMapping("/api/redisson/")
@RestController
public class RedissonController {

    private final LockTemplate lockTemplate;
    private final RedissonUtil redissonUtil;

    private final Map<String, String> cache = Maps.newHashMap();

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        final LockInfo lock = lockTemplate.lock(key, -1, -1);
        if (null == lock) {
            throw new RuntimeException("业务处理中,请稍后再试");
        }

        try {
            ThreadUtil.safeSleep(10 * 1000L);
            cache.put(key, "aaaaaaaaaaaaaa");
        } finally {
            lockTemplate.releaseLock(lock);
        }

//        RReadWriteLock readWriteLock = lockTemplate.getReadWriteLock(key);
//        RLock rLock = readWriteLock.readLock();
//        try {
//            if (rLock.tryLock(10, -1, TimeUnit.SECONDS)) {
//                try {
//                    //Thread.sleep(10 * 1000L);
//                    return cache.get(key);
//                } finally {
//                    rLock.unlock();
//                }
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException("获取锁失败",e);
//        }

        return cache.get(key);
    }

    @GetMapping("/get2")
    public String post(@RequestParam String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        RLock lock = redissonUtil.getLock(key);
        try {
            if (lock.tryLock(30, -1, TimeUnit.SECONDS)) {
                try {
                    ThreadUtil.safeSleep(10 * 1000L);
                    cache.put(key, "aaaaaaaaaaaaaa");
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return cache.get(key);
    }

}
