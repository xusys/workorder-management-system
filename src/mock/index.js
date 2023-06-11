import Mock from 'mockjs';

// 模拟延迟
Mock.setup({
  timeout:500
})

// 生成随机数据
Mock.mock('/api/v1/dataSource',function(){
  return Mock.mock({
    'code':0,
    'msg':'',
    'result':{
      'list|1':[{
        'user_name':'@cname',
        'password|1': '@String'
      }]
    }
  })
})


