import request from '@/utils/request';

var prefix = '/api/integration/buildGit/' ;
var managerUrl = {
    datatables : prefix +"datatables" ,
    createUrl: prefix + 'add' ,
    saveUrl: prefix + 'saveEntity' ,
    updateUrl: prefix +"modify" ,
    statusUrl: prefix +"changeStatus" ,
    cleanUrl: prefix + "clean",
    detailUrl: prefix +"detail",
    removeUrl: prefix + "delete" ,
    exportUrl: prefix + "exportExcel",
    bingFormGitlab: prefix + "bingFormGitlab",
    unBing: prefix + "unBing",
    getGithubAuthurl: prefix + "getGithubAuthurl",
    checkIfUsed: prefix + "checkGitIfUsed"
}

// 查询列表
export function listBuildGit(query) {
  return request({
    url: managerUrl.datatables ,
    method: 'post',
    params: query
  })
}

// 查询详细
export function getBuildGit(id) {
  return request({
    url: managerUrl.detailUrl + '/' + id ,
    method: 'get'
  })
}

// 新增
export function addBuildGit(data) {
  return request({
    url: managerUrl.saveUrl,
    method: 'post',
    data: data
  })
}

// 修改
export function updateBuildGit(data) {
  return request({
    url: managerUrl.updateUrl,
    method: 'put',
    data: data
  })
}

// 删除
export function delBuildGit(id) {
  return request({
    url: managerUrl.removeUrl + '/' + id ,
    method: 'delete'
  })
}

// 导出
export function exportBuildGit(query) {
  return request({
    url: managerUrl.exportUrl,
    method: 'get',
    params: query
  })
}

// 状态修改
export function changeStatusBuildGit(id , status) {
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


// 保存gitlab绑定
export function bingFormGitlab(data) {
  return request({
    url: managerUrl.bingFormGitlab,
    method: 'post',
    data: data
  })
}

// 获取应用列表
export function unBing(id , gitType) {
  return request({
    url: managerUrl.unBing + "?id=" + id + "&gitType=" + gitType,
    method: 'get',
  })
}


// 获取github绑定链接
export function getGithubAuthurl(gitType){
  return request({
    url: managerUrl.getGithubAuthurl + "?gitType=" + gitType,
    method: 'get',
  })
}


//检查仓库是否已被引用
export function checkIfUsed(ids) {
  return request({
    url: managerUrl.checkIfUsed + '?ids=' + ids ,
    method: 'get'
  })
}
