package com.xuanxi.backend.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.xuanxi.backend.common.BaseResponse;
import com.xuanxi.backend.common.ResultUtils;
import com.xuanxi.backend.dao.Account;
import com.xuanxi.backend.exception.ErrCode;
import com.xuanxi.backend.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountMapper accountMapper;

    @GetMapping("/list")
    public BaseResponse<List<Account>> getAccount() {
        return ResultUtils.success(accountMapper.selectAll());
    }
}
