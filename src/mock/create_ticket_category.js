import Mock from "mockjs";

// 模拟延迟
Mock.setup({
  timeout: 500,
});

Mock.mock("/admin/v1/ticket/create_ticket_category", function () {
  return Mock.mock({
    code: 0,
    msg: "",
    result:{
      "list|4":[
        {
          "category_id|+1": 1,
          name: "@String",
        }
      ]
    }
  });
});
