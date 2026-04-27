package com.example.jiajiebang.controller;

import com.example.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/jiajiebang")
@Tag(name = "家洁帮服务", description = "家洁帮小程序相关接口")
public class JiajiebangController {

    @GetMapping("/home")
    @Operation(summary = "获取首页信息", description = "获取家洁帮小程序首页数据")
    public Result<Map<String, Object>> getHomeInfo() {
        Map<String, Object> homeData = new HashMap<>();
        homeData.put("banner", new String[]{
            "https://example.com/banner1.jpg",
            "https://example.com/banner2.jpg"
        });
        homeData.put("services", new String[]{"保洁", "家政", "维修", "搬家"});
        homeData.put("message", "欢迎使用家洁帮小程序");
        
        return Result.success(homeData);
    }
}
