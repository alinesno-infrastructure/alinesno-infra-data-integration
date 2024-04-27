function baseWrapper (type, field) {
  return `condition[${field}|${type}]`
}

function likeBuilder (field) {
  return baseWrapper('like', field)
}

function eqBuilder (field) {
  return baseWrapper('eq', field)
}

function neBuilder (field) {
  return baseWrapper('ne', field)
}

function rangeDateBuilder (field) {
  const geTime = baseWrapper('geTime', field)
  const leTime = baseWrapper('leTime', field)
  return [geTime, leTime]
}

function rangeSizeBuilder (field) {
  const ge = baseWrapper('ge', field)
  const le = baseWrapper('le', field)
  return [ge, le]
}

function containsBuilder (field) {
  return baseWrapper('in', field)
}

function geBuilder (field) {
  return baseWrapper('ge', field)

}

function gtBuilder (field) {
  return baseWrapper('gt', field)

}

function leBuilder (field) {
  return baseWrapper('le', field)

}

function ltBuilder (field) {
  return baseWrapper('lt', field)
}

function geTimeBuilder (field) {
  return baseWrapper('geTime', field)
}

function gtTimeBuilder (field) {
  return baseWrapper('gtTime', field)
}

function leTimeBuilder (field) {
  return baseWrapper('leTime', field)
}

function ltTimeBuilder (field) {
  return baseWrapper('ltTime', field)
}

function orderByBuilder (field) {
  return baseWrapper('orderBy', field)

}

const Condition = {
  /**
   * 模糊搜索
   */
  like: () => likeBuilder,
  /**
   * 相等搜索
   */
  eq: () => eqBuilder,
  /**
   * 不等搜索
   */
  ne: () => neBuilder,
  /**
   * 时间范围搜索
   */
  rangeDate: () => rangeDateBuilder,
  /**
   * 数值范围搜索
   */
  rangeSize: () => rangeSizeBuilder,
  /**
   * 包含搜索
   */
  contains: () => containsBuilder,
  /**
   * 大于等于搜索
   */
  ge: () => geBuilder,
  /**
   * 大于搜索
   */
  gt: () => gtBuilder,
  /**
   * 小等于搜索
   */
  le: () => leBuilder,
  /**
   * 小于搜索
   *
   */
  lt: () => ltBuilder,
  /**
   * 时间字段大于等于搜索
   */
  geTime: () => geTimeBuilder,
  /**
   * 时间字段大于搜索
   */
  gtTime: () => gtTimeBuilder,
  /**
   * 时间字段小于等于搜索
   */
  leTime: () => leTimeBuilder,
  /**
   * 时间字段小于搜索
   */
  ltTime: () => ltTimeBuilder,
  /**
   * 排序字段
   */
  orderBy: () => orderByBuilder()
}
export default Condition
