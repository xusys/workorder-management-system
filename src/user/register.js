import React, { useRef, useEffect, useState } from "react";
import {
  Form,
  Input,
  Button,
  Radio,
  Dropdown,
  MessagePlugin,
  Select,
} from "tdesign-react";
import { DesktopIcon, LockOnIcon, Icon } from "tdesign-icons-react";
import { useNavigate } from "react-router-dom";
import "./user.css";
import axios from "axios";
import "../mock/register";
// import axios from "./axiosInstance";
const { FormItem } = Form;

export default function Register(props) {
  const formRef = useRef();
  const navigate = useNavigate();

  const onSubmit = (e) => {
    if (e.validateResult === true) {
      let params = formRef.current.getFieldsValue(true);

      delete params.rePassword;

      console.log("params", params);

      axios
        .post("/v1/user/register", params)
        .then((res) => {
          if (res.data.code === 0) {
            // 注册成功，转到登陆界面
            navigate("/");
          }
        })
        .catch((error) => {
          // 处理请求错误
          console.log("请求错误", error);
        });
    }
  };

  function rePassword(val) {
    return new Promise((resolve) => {
      const timer = setTimeout(() => {
        resolve(formRef.current.getFieldValue("password") === val);
        clearTimeout(timer);
      });
    });
  }

  // 异步函数，用于获取分类列表数据并储存
  //   async function fetchCtgList() {
  //     try {
  //       let { data } = await api.get("/admin/v1/ticket/category");
  //       setCtgListValue(data);
  //     } catch (err) {
  //       console.log("err", err);
  //       // setCtgListValue([]);
  //       setCtgListValue([
  //         { category_id: "1", name: "Category 1" },
  //         { category_id: "2", name: "Category 2" },
  //       ]);
  //     }
  //   }

  //   const [ctgListValue, setCtgListValue] = useState([]);
  //   const onChange = (value) => {
  //     setCtgListValue(value);
  //   };

  // 分类多个单选框创建
  //   const categoryGroup = ctgListValue.map((i) => {
  //     return (
  //       <Radio value={i.category_id} key={i.category_id}>
  //         {i.city}
  //       </Radio>
  //     );
  //   });

  //   useEffect(() => {
  //     setCtgListValue(locationArr);
  //   }, []);

  //   const clickHandler = (data) => {
  //     MessagePlugin.success(`选中【${data.value}】`);
  //   };

  const [city_value, setcity_Value] = useState("");
  const [area_value, setarea_Value] = useState("");
  const [area_show, setArea_show] = useState(false);
  const [districtOptions, setDistrictOptions] = useState([]);

  const city_onChange = (value) => {
    setarea_Value(""); // 清空第二个选单的选中值
    setDistrictOptions([]); // 清空第二个选单的选项
    setcity_Value(value);
    // console.log("city", value);
    // console.log("area", area_value);

    if (value != null) {
      setArea_show(true);
      setDistrictOptions([]);
      const selectedCity = locationArr.find(
        (item) => item.category_id === value
      );
      if (selectedCity) {
        setDistrictOptions(selectedCity.districts);
      }
    } else {
      setArea_show(false);
      setDistrictOptions([]);
    }

    // setarea_Value(""); // 清空第二个选单的选中值
    // setDistrictOptions([]); // 清空第二个选单的选项
    // console.log("area", area_value);
  };

  const area_onChange = (value) => {
    setarea_Value(value || "");
    // console.log("area", value);
    if ((value != null) & (value !== "")) {
      setArea_show(true);
    } else {
      setArea_show(false);
    }
  };

  const locationArr = [
    {
      category_id: "1",
      city: "广州省",
      districts: [{ category_id: "0", area: "省直属" }],
    },
    {
      category_id: "2",
      city: "广州市",
      districts: [
        { category_id: "0", area: "市直属" },
        { category_id: "1", area: "番禺区" },
        { category_id: "2", area: "天河区" },
        { category_id: "3", area: "越秀区" },
      ],
    },
    {
      category_id: "3",
      city: "韶关市",
      districts: [
        { category_id: "0", area: "市直属" },
        { category_id: "1", area: "x x县" },
        { category_id: "2", area: "h h县" },
      ],
    },
  ];

  const options = locationArr.map((item) => ({
    label: item.city,
    value: item.category_id,
  }));

  useEffect(() => {
    console.log("newest_area", area_value);
  }, [area_value]);

  return (
    <Form
      ref={formRef}
      layout="vertical"
      onSubmit={onSubmit}
      labelWidth={0}
      colon={true}
      scrollToFirstError="smooth"
    >
      <FormItem
        name="user_name"
        rules={[
          { required: true, message: "用户名必填", type: "error" },
          { pattern: /^[A-Za-z0-9]+$/, message: "用户名只能是英文和数字" },
          { min: 4, message: "至少需要4个字符", type: "error" },
        ]}
      >
        <Input
          placeholder="请输入用户名"
          size="large"
          clearable
          prefixIcon={<DesktopIcon />}
        />
      </FormItem>
      <FormItem
        name="password"
        rules={[
          { required: true, message: "密码必填", type: "error" },
          { min: 6, message: "至少需要6位", type: "error" },
        ]}
      >
        <Input
          placeholder="请输入密码"
          type="password"
          size="large"
          type="password"
          prefixIcon={<LockOnIcon />}
          clearable
        />
      </FormItem>
      <FormItem
        name="rePassword"
        rules={[
          { required: true, message: "密码必填", type: "error" },
          { validator: rePassword, message: "两次密码不一致" },
        ]}
      >
        <Input
          placeholder="请重复密码"
          type="password"
          size="large"
          type="password"
          prefixIcon={<LockOnIcon />}
          clearable
        />
      </FormItem>
      <FormItem label="市" name="city_category">
        {/* <Radio.Group>{categoryGroup}</Radio.Group> */}
        <Select
          value={city_value}
          onChange={city_onChange}
          style={{ width: "40%" }}
          clearable
          options={options}
        />
      </FormItem>
      <FormItem label="区/县" name="area_category">
        {/* <Radio.Group>{categoryGroup}</Radio.Group> */}
        <Select
          key={area_value} // 添加key属性
          value={area_value}
          onChange={area_onChange}
          style={{ width: "40%" }}
          clearable
          options={districtOptions.map((district) => ({
            label: district.area,
            value: district.category_id,
          }))}
          disabled={!area_show}
        />
      </FormItem>

      <FormItem statusIcon={false}>
        <Button size="large" theme="primary" type="submit" block>
          注册
        </Button>
      </FormItem>
    </Form>
  );
}
