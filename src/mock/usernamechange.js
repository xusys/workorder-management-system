import Mock from 'mockjs';

// 模拟延迟
Mock.setup({
  timeout:500
})

Mock.mock('/api/v1/updateUser', 'post', function (options) {
    const { username } = options; // 解析请求体中的用户名和密码
    // if (username === 'admin') {
      // 验证成功
      return {
        code: 0
      };
    // } else {
    //   // 验证失败
    //   return {
    //     code: -1
    //   };
    // }
  });