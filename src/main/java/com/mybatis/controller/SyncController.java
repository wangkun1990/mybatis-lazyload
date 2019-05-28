package com.mybatis.controller;

import com.mybatis.service.impl.SyncServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RequestMapping
@RestController
public class SyncController {

    @Autowired
    private SyncServiceImpl syncService;

    @GetMapping(value = "/async")
    public void async() {
        syncService.async();
    }

    @GetMapping(value = "/sync")
    public void sync() {
        syncService.sync();
    }


    @GetMapping(value = "/async1")
    public void async1() {
        syncService.async1();
    }


    @GetMapping(value = "/deferredResult")
    public DeferredResult<String> deferredResult() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResult.setResult("OK");
        deferredResult.setResultHandler((Object o) -> {
            System.out.println(o);
        });
        return deferredResult;
    }
}
