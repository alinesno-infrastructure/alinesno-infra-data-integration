import request from '@/utils/request';

/**
 * 【请填写功能名称】 接口文件
 *
 * @author paul
 * @version 1.0.0
 */

// 接口配置项
var prefix = '/api/integration/Job/';
var managerUrl = {
  datatables: prefix + "datatables",
  createUrl: prefix + 'add',
  saveUrl: prefix + 'newSave',
  updateUrl: prefix + "newUpdate",
  statusUrl: prefix + "changeStatus",
  cleanUrl: prefix + "clean",
  detailUrl: prefix + "detail",
  removeUrl: prefix + "delete",
  exportUrl: prefix + "exportExcel",
  changeField: prefix + "changeField",
  getSuccessJob: prefix + "getSuccessJob",
}

// 查询【请填写功能名称】列表
export function getSuccessJob(query, data) {
  return request({
    url: managerUrl.getSuccessJob,
    method: 'post',
    params: query,
    data: data
  })
}

// 查询【请填写功能名称】列表
export function listJob(query, data) {
  return request({
    url: managerUrl.datatables,
    method: 'post',
    params: query,
    data: data
  })
}

// 查询【请填写功能名称】详细
export function getJob(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addJob(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateJob(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delJob(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportJob(query) {
  return request({
    url: managerUrl.exportUrl,
    method: 'get',
    params: query
  })
}

// 状态修改
export function changeStatusBasicSettings(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: managerUrl.statusUrl,
    method: 'put',
    data: data
  })
}

/**
 * 运行转换
 * @param id 转换id
 * @returns {AxiosPromise}
 */
export function handleRun(id) {
  return request({
    url: prefix + "startQuartzJob/" + id,
    method: 'post'
  })
}

/**
 * 状态修改
 * @param data
 * @returns {AxiosPromise}
 */
export function changeFile(data) {
  return request({
    url: managerUrl.changeField,
    method: 'post',
    data: data
  })
}


/**
 * 停止作业任务
 * @param id 任务id
 * @returns {AxiosPromise}
 */
export function handleStop(id) {
  return request({
    url: prefix + "stopQuartzJob/" + id,
    method: 'post'
  })
}
