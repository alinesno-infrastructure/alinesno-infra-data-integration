import request from '@/utils/request';

/**
 * 【请填写功能名称】 接口文件
 *
 * @author paul
 * @date 2024年3月10日
 */

// 接口配置项
var prefix = '/api/integration/JobMonitor/' ;
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
export function listJobMonitor(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getJobMonitor(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id ,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addJobMonitor(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateJobMonitor(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delJobMonitor(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id ,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportJobMonitor(query) {
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
//获取作业任务执行结果
export function getJobCount(query)
{
  return request({
    url: prefix + 'taskCount',
    method: 'get',
    params: query
  })
}
