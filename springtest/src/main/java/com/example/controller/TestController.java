package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String index() {

        String sql = "SELECT mobile FROM usertest WHERE id = ?";

        // 通过jdbcTemplate查询数据库
        String mobile = (String)jdbcTemplate.queryForObject(
                sql, new Object[] { 1 }, String.class);

        return "Hello " + mobile;
    }
}