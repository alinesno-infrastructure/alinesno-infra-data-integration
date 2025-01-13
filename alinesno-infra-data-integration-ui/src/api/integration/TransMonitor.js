import request from '@/utils/request';

/**
 * 【请填写功能名称】 接口文件
 *
 * @author paul
 * @version 1.0.0
 */

// 接口配置项
var prefix = '/api/integration/TransMonitor/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'save' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
}

// 查询【请填写功能名称】列表
export function listTransMonitor(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getTransMonitor(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id ,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addTransMonitor(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateTransMonitor(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delTransMonitor(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id ,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportTransMonitor(query) {
  return request({
    url: managerUrl.exportUrl,
    method: 'get',
    params: query
  })
}

// 状态修改
export function changeStatusBasicSettings(id , status) {
  const data = {
    id ,
    status
  }
  return request({
    url: managerUrl.statusUrl,
    method: 'put',
    data: data
  })
}

// 查询所有任务分类
export function getCategoryList() {
  return request({
    url: prefix + 'getList',
    method: 'get'
  })
}


//获取作业任务执行结果
export function getTaskCount(query)
{
  return request({
    url: prefix + 'taskCount',
    method: 'get',
    params: query
  })
}

