import Mock from 'mockjs';

// 模拟延迟
Mock.setup({
  timeout:500
})

Mock.mock('/api/v1/create_ticket', 'post', function (options) {
    console.log('options',options);
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