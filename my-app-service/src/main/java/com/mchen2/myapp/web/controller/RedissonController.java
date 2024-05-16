package com.mchen2.myapp.web.controller;

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

    private final RedissonUtil redissonUtil;

    private final Map<String, String> cache = Maps.newHashMap();

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        RReadWriteLock readWriteLock = redissonUtil.getReadWriteLock(key);
        RLock rLock = readWriteLock.readLock();
        try {
            if (rLock.tryLock(10, -1, TimeUnit.SECONDS)) {
                try {
                    //Thread.sleep(10 * 1000L);
                    return cache.get(key);
                } finally {
                    rLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("获取锁失败",e);
        }

        return cache.get(key);
    }

    @PostMapping("/post")
    public String post(@RequestParam String key, @RequestParam String value) {
        RReadWriteLock readWriteLock = redissonUtil.getReadWriteLock(key);
        RLock rLock = readWriteLock.writeLock();
        try {
            if (rLock.tryLock(10, -1, TimeUnit.SECONDS)) {
                try {
                    Thread.sleep(10 * 1000L);
                    cache.put(key, value);
                    return cache.get(key);
                } finally {
                    rLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("获取锁失败",e);
        }

        return cache.get(key);
    }

}
