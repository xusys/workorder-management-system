import Mock from 'mockjs';

// 模拟延迟
Mock.setup({
  timeout:500
})

// 生成随机数据
// Mock.mock('/api/v1/dataSource',function(){
//   return Mock.mock({
//     'code':0,
//     'msg':'',
//     'result':{
//       'list|1':[{
//         'user_id|0-2': 1,
//         'user_name':'@cname',
//         'token':'@string',
//         'password|1': '@String'
//       }]
//     }
//   })
// })

Mock.mock('/api/v1/login', 'post', function (options) {
  const { username, password } = JSON.parse(options.body); // 解析请求体中的用户名和密码
  // 在这里进行用户名和密码的验证逻辑，根据验证结果返回相应的数据
  if (username === 'admin' && password === '123456') {
    // 验证成功
    return {
      code: 0,
      msg: '登录成功',
      result: {
        user_id: 1,
        user_name: 'admin',
        token: 'boomxakalaka'
      }
    };
  } else {
    // 验证失败
    return {
      code: -1,
      msg: '用户名或密码错误',
      result: null
    };
  }
});