import Mock from "mockjs";

// 模拟延迟
Mock.setup({
  timeout: 500,
});

Mock.mock("/admin/v1/ticket/workstation_category", function () {
  // const { username } = options; // 解析请求体中的用户名和密码
  // if (username === 'admin') {
  // 验证成功
  return Mock.mock({
    code: 0,
    msg: "",
    // [
    //   { category_id: "1", name: "Category 1" },
    //   { category_id: "2", name: "Category 2" },
    // ]
    result:{
      "list|3":[
        {
          "category_id|+1": 1,
          name: "@String",
        }
      ]
    }
  });
  // } else {
  //   // 验证失败
  //   return {
  //     code: -1
  //   };
  // }
});
