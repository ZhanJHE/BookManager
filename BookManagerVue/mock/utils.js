/**
 * 将 URL 查询字符串转换为对象的工具函数
 * @param {string} url - 完整的 URL 字符串
 * @returns {Object} - 转换后的参数对象
 */
function param2Obj(url) {
  // 解码 URL，并获取查询字符串部分（? 后面的内容）
  const search = decodeURIComponent(url.split('?')[1]).replace(/\+/g, ' ')
  // 如果没有查询字符串，返回空对象
  if (!search) {
    return {}
  }
  const obj = {}
  // 按 '&' 分割查询字符串，得到参数数组
  const searchArr = search.split('&')
  // 遍历参数数组
  searchArr.forEach(v => {
    const index = v.indexOf('=')
    if (index !== -1) {
      const name = v.substring(0, index) // 获取参数名
      const val = v.substring(index + 1, v.length) // 获取参数值
      obj[name] = val // 将参数名和值存入对象
    }
  })
  return obj
}

// 导出该工具函数
module.exports = {
  param2Obj
}