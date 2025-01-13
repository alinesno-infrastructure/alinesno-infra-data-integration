package com.alinesno.infra.data.integration.api.controller;

import cn.hutool.json.JSONObject;
import com.alinesno.infra.common.core.constants.SpringInstanceScope;
import com.alinesno.infra.common.facade.response.AjaxResult;
import com.alinesno.infra.data.integration.enums.LogLevelEnum;
import com.alinesno.infra.data.integration.enums.RunResultEnum;
import com.alinesno.infra.data.integration.enums.RunStatusEnum;
import com.alinesno.infra.data.integration.enums.RunTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 获取枚举类型的公共接口
 *
 * @author 梁家铭
 * @date 2021年7月28日
 */
@RestController
@Scope(SpringInstanceScope.PROTOTYPE)
@RequestMapping("/api/commons/enums")
public class CommonsEnumRest {

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(CommonsEnumRest.class);


    /**
     * 获取脚本运行类型枚举类型的接口
     *
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @GetMapping("/getRunTypeList")
    public AjaxResult getRunTypeEnumList() {
        ArrayList<Object> list = new ArrayList<>();
        Arrays.stream(RunTypeEnum.values()).forEach(anEnum -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", anEnum.getDesc());
            jsonObject.put("value", anEnum.getCode());
            list.add(jsonObject);
        });

        return AjaxResult.success(list);
    }

    /**
     * 获取日志级别类型枚举类的接口
     *
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @GetMapping("/getLogLevelList")
    public AjaxResult getLogLevelEnumList() {
        ArrayList<Object> list = new ArrayList<>();
        Arrays.stream(LogLevelEnum.values()).forEach(anEnum -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", anEnum.getDesc());
            jsonObject.put("value", anEnum.getCode());
            list.add(jsonObject);
        });

        return AjaxResult.success(list);
    }

    /**
     * 获取日志级别类型枚举类的接口
     *
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @GetMapping("/getStatusList")
    public AjaxResult getStatusList() {
        ArrayList<Object> list = new ArrayList<>();
        Arrays.stream(RunStatusEnum.values()).forEach(anEnum -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", anEnum.getDesc());
            jsonObject.put("value", anEnum.getCode());
            list.add(jsonObject);
        });

        return AjaxResult.success(list);
    }


    /**
     * 获取执行结果枚举类的接口
     *
     * @return {@link AjaxResult}
     */
    @ResponseBody
    @GetMapping("/getRunResultList")
    public AjaxResult getRunResultList() {
        ArrayList<Object> list = new ArrayList<>();
        Arrays.stream(RunResultEnum.values()).forEach(anEnum -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", anEnum.getDesc());
            jsonObject.put("value", anEnum.getCode());
            list.add(jsonObject);
        });

        return AjaxResult.success(list);
    }

}
