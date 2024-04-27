package com.alinesno.infra.common.web.adapter.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.common.web.adapter.dto.LoginBodyDto;
import com.alinesno.infra.common.web.adapter.dto.menus.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class CommonLoginController {

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBodyDto loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = UUID.randomUUID().toString() ;
        ajax.put(TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {

        Map<String, Object> data = new HashMap<>();
        // 将数据填充到data中...
        data.put("permissions", new String[]{"*:*:*"});

        Map<String, Object> user = new HashMap<>();
        user.put("createBy", "admin");
        user.put("createTime", "2023-04-23 16:11:38");
        user.put("updateBy", null);
        user.put("updateTime", null);
        user.put("remark", "管理员");
        user.put("userId", 1);
        user.put("deptId", 103);
        user.put("userName", "admin");
        user.put("nickName", "AIP技术团队");
        user.put("email", "aip-team@163.com");
        user.put("phonenumber", "15888888888");
        user.put("sex", "1");
        user.put("avatar", "");
        user.put("password", "");
        user.put("status", "0");
        user.put("delFlag", "0");
        user.put("loginIp", "");
        user.put("loginDate", "2023-09-21T16:54:12.000+08:00");

        Map<String, Object> dept = new HashMap<>();
        dept.put("createBy", null);
        dept.put("createTime", null);
        dept.put("updateBy", null);
        dept.put("updateTime", null);
        dept.put("remark", null);
        dept.put("deptId", 103);
        dept.put("parentId", 101);
        dept.put("ancestors", "0,100,101");
        dept.put("deptName", "研发部门");
        dept.put("orderNum", 1);
        dept.put("leader", "AIP技术团队");
        dept.put("phone", null);
        dept.put("email", null);
        dept.put("status", "0");
        dept.put("delFlag", null);
        dept.put("parentName", null);
        dept.put("children", new Object[]{});

        user.put("dept", dept);

        Map<String, Object> role = new HashMap<>();
        role.put("createBy", null);
        role.put("createTime", null);
        role.put("updateBy", null);
        role.put("updateTime", null);
        role.put("remark", null);
        role.put("roleId", 1);
        role.put("roleName", "超级管理员");
        role.put("roleKey", "admin");
        role.put("roleSort", 1);
        role.put("dataScope", "1");
        role.put("menuCheckStrictly", false);
        role.put("deptCheckStrictly", false);
        role.put("status", "0");
        role.put("delFlag", null);
        role.put("flag", false);
        role.put("menuIds", null);
        role.put("deptIds", null);
        role.put("permissions", null);
        role.put("admin", true);

        user.put("roles", new Object[]{role});

        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", user.get("roles"));
        ajax.put("permissions", data.get("permissions"));

        return ajax;
    }

    /**
     * 获取路由信息
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {

//        Menu dashboardMenu = new Menu("Dashboard", "/dashboard", false, "noRedirect", "Layout", true, new Menu.Meta("仪盘表", "dashboard", false, null), List.of(
//                new Menu("Dashboard", "index", false, false , "dashboard", new Menu.Meta("概览", "dashboard", false, null))
//        ));

        Menu integrationMenu = new Menu("Integration", "/integration", false, "noRedirect", "Layout", true, new Menu.Meta("异构数据集成服务", "system", false, null), List.of(
                new Menu("BuildGit", "integration/buildGit/list", false, false , "integration/buildGit/list", new Menu.Meta("git仓库", "druid", false, null)),
                new Menu("Category", "integration/category/list", false, false , "integration/category/list", new Menu.Meta("任务分类", "peoples", false, null)),
//                new Menu("Dashboard", "integration/dashboard/list", false, false , "integration/dashboard/list", new Menu.Meta("仪表盘", "online", false, null)),
               new Menu("Job", "integration/job/list", false, false , "integration/job/list", new Menu.Meta("作业任务", "user", false, null)),
                new Menu("JobMonitor", "integration/jobMonitor/list", false, false , "integration/jobMonitor/list", new Menu.Meta("作业任务监控", "peoples", false, null)),
                new Menu("jobRunHis", "integration/jobRunHis/list", false, false , "integration/jobRunHis/list", new Menu.Meta("作业任务历史", "druid", false, null)),
                new Menu("Quartz", "integration/quartz/list", false, false , "integration/quartz/list", new Menu.Meta("定时调度策略", "peoples", false, null)),
                new Menu("Trans", "integration/trans/list", false, false , "integration/trans/list", new Menu.Meta("转换任务", "online", false, null)),
                new Menu("TransMonitor", "integration/transMonitor/list", false, false , "integration/transMonitor/list", new Menu.Meta("转换任务监控", "user", false, null)),
                new Menu("TransRunHis", "integration/transRunHis/list", false, false , "integration/transRunHis/list", new Menu.Meta("转换任务历史", "peoples", false, null))
       ));




        List<Menu> menus = List.of( integrationMenu ) ;
        String jsonString = JSON.toJSONString(menus, SerializerFeature.WriteMapNullValue);

        return AjaxResult.success(JSONArray.parseArray(jsonString)) ;
    }

}
