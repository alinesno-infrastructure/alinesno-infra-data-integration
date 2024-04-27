import request from '@/utils/request';

/**
 * 【请填写功能名称】 接口文件
 *
 * @author paul
 * @date 2024年3月10日
 */

// 接口配置项
var prefix = '/api/integration/Trans/';
var managerUrl = {
  datatables: prefix + "datatables",
  createUrl: prefix + 'add',
  saveUrl: prefix + 'save',
  updateUrl: prefix + "modify",
  statusUrl: prefix + "changeStatus",
  cleanUrl: prefix + "clean",
  detailUrl: prefix + "detail",
  removeUrl: prefix + "delete",
  exportUrl: prefix + "exportExcel",
  changeField: prefix + "changeField",
  checkIfUsed: prefix + "checTransIfUsed"
}

// 查询【请填写功能名称】列表
export function listTrans(query, data) {
  return request({
    url: managerUrl.datatables,
    method: 'post',
    params: query,
    data: data
  })
}

// 查询【请填写功能名称】详细
export function getTrans(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addTrans(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateTrans(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delTrans(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportTrans(query) {
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

// 状态修改
export function changeFile(data) {
  return request({
    url: managerUrl.changeField,
    method: 'post',
    data: data
  })
}

// 状态修改
export function getTransList() {
  return request({
    url: prefix + "getList",
    method: 'get'
  })
}

/**
 * 运行转换
 * @param id 转换id
 * @returns {AxiosPromise}
 */
export function handleRun(id) {
  return request({
    url: prefix + "startQuartzTrans/" + id,
    method: 'post'
  })
}

/**
 * 停止转换任务
 * @param id 转换id
 * @returns {AxiosPromise}
 */
export function handleStop(id) {
  return request({
    url: prefix + "stopQuartzTrans/" + id,
    method: 'post'
  })
}


//检查转换任务是否已被引用
export function checkIfUsed(ids) {
  return request({
    url: managerUrl.checkIfUsed + '?ids=' + ids ,
    method: 'get'
  })
}
