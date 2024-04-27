import request from '@/utils/request';

/**
 * 【请填写功能名称】 接口文件
 *
 * @author paul
 * @date 2024年3月10日
 */

// 接口配置项
var prefix = '/api/integration/Quartz/' ;
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
  checkIfUsed: prefix + "checkQuartzIfUsed"
}

// 查询【请填写功能名称】列表
export function listQuartz(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getQuartz(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id ,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addQuartz(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateQuartz(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delQuartz(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id ,
    method: 'delete'
  })
}

// 导出【请填写功能名称】
export function exportQuartz(query) {
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


//检查定时策略是否已被引用
export function checkIfUsed(ids) {
  return request({
    url: managerUrl.checkIfUsed + '?ids=' + ids ,
    method: 'get'
  })
}
