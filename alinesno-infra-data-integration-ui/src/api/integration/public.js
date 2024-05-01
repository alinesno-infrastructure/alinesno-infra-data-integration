import request from '@/utils/request';

/**
 *
 * 公共接口
 *
 * @author 梁家铭
 * @date 2021年7月28日
 */

// 接口配置项
var prefix = '/api/integration/';
let commonsEnums = '/api/commons/enums/'

// 查询所有任务分类
export function getCategoryList() {
  return request({
    url: prefix + 'Category/getList',
    method: 'get'
  })
}

// 获取所有定时策略的接口
export function getQuartzList() {
  return request({
    url: prefix + 'Quartz/getList',
    method: 'get'
  })
}

// 查询脚本运行类型枚举类型的接口
export function getRunTypeList() {
  return request({
    url: commonsEnums + 'getRunTypeList',
    method: 'get'
  })
}

// 查询日志级别类型枚举类的接口
export function getLogLevelList() {
  return request({
    url: commonsEnums + 'getLogLevelList',
    method: 'get'
  })
}

// 查询日志级别类型枚举类的接口
export function getStatusList() {
  return request({
    url: commonsEnums + 'getStatusList',
    method: 'get'
  })
}


// 查询日志级别类型枚举类的接口
export function getRunResultList() {
  return request({
    url: commonsEnums + 'getRunResultList',
    method: 'get'
  })
}
