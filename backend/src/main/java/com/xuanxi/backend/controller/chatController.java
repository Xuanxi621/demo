package com.xuanxi.backend.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class chatController {

    private static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";
    private static final String API_KEY = "";

    @CrossOrigin
    @PostMapping("/chat")
    public String chat(@RequestParam String content) {
        JSONObject payload = new JSONObject();
        payload.set("model", "qwen-plus");
        payload.set("messages", new Object[]{
                new JSONObject().set("role", "system").set("content", "You are a helpful assistant."),
                new JSONObject().set("role", "user").set("content", content)
        });

        HttpResponse response = HttpRequest.post(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .execute();

        return response.body();
    }
}
