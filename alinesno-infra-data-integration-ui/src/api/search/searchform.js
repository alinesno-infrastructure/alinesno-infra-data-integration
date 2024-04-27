/**
 * 搜索条件处理
 * @param config 字段配置
 * @param queryDataObj 数据对象
 */

export default function searchParam (config, queryDataObj) {
  if (config == null || queryDataObj == null) {
    return queryDataObj
  }
  //处理后的搜索对象
  const processedParams = {}
  //查询字段列表
  const queryFields = Object.keys(queryDataObj)
  queryFields.forEach(key => {
    if (config.hasOwnProperty(key)) {
      const fields = config[key](key)
      if (fields instanceof Array && fields.length === 2) {
        const values = queryDataObj[key]
        processedParams[fields[0]] = values ? values[0] : null
        processedParams[fields[1]] = values ? values[1] : null
      } else {
        processedParams[fields] = queryDataObj[key]
      }
    } else {
      //不在配置对象的字段原样传输到后端
      processedParams[key] = queryDataObj[key]
    }
  })
  return processedParams
}


