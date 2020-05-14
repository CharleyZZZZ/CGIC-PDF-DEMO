/**
 * GET 拼接查询数据方法
 * @param nAxios
 * @param url
 * @param query
 * @returns {Promise}
 */
const queryData = (nAxios, url, query) => {
  let tempQuery = ''
  if (url.indexOf('?') === -1) {
    tempQuery = '?'
  }
  for (let key in query) {
    if (query[key] !== null && query[key] !== undefined && query[key] !== '' && query[key].length !== 0) {
      tempQuery += '&' + key + '=' + query[key]
    }
  }
  let listPromise = new Promise((resolve, reject) => {
    nAxios.get(encodeURI(url + tempQuery)).then((rst) => {
      resolve(rst)
    }).catch(error => {
      reject(error)
    })
  })
  return listPromise
}

/**
 * 公用方法集合
 */
const requestData = (nAxios, methods, url, datas, headers, timeout) => {
  let options = Object.assign({}, {
    url: url,
    method: methods,
    data: datas
  })
  if (headers) {
    options.headers = headers
  }
  if (timeout) {
    options.timeout = timeout
  }
  let listPromise = new Promise((resolve, reject) => {
    nAxios.request(options)
      .then(res => {
        resolve(res)
      }).catch(res => {
      reject(res)
    })
  })
  return listPromise
}

export {
  queryData,
  requestData
}
