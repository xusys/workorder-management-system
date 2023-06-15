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
import data from './output.json'

// import "../mock/register";
// import axios from "./axiosInstance";
const { FormItem } = Form;

export default function Register(props) {

  const locationArr = data;
  // [
  //   {
  //     category_id: "00",
  //     city: "广州省",
  //     districts: [{ category_id: "00", area: "总部" }],
  //   },
  //   {
  //     category_id: "01",
  //     city: "广州市",
  //     districts: [
  //       { category_id: "00", area: "总部" },
  //       { category_id: "01", area: "越秀区" },
  //       { category_id: "02", area: "海珠区" },
  //       { category_id: "03", area: "荔湾区" },
  //     ],
  //   },
  // ];



  const formRef = useRef();
  const navigate = useNavigate();

  const onSubmit = (e) => {
    if (e.validateResult === true) {
      let params = formRef.current.getFieldsValue(true);

      delete params.rePassword;

      params.areaId += params.area_category;

      delete  params.area_category;

      console.log("params", params);

      axios
        .post("http://localhost:8080/user/register", params)
        .then((res) => {
          if (res.data.code === 1) {
            console.log("success register")
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

  
  const [workstation_value, setworkstation_Value] = useState("");
  const [workstationArr, setCtgListValue] = useState([]);
  const [city_value, setcity_Value] = useState("");
  const [area_value, setarea_Value] = useState("");
  const [area_show, setArea_show] = useState(false);
  const [districtOptions, setDistrictOptions] = useState([]);

    // 异步函数，用于获取分类列表数据并储存
    async function fetchCtgList() {
      try {
        let { data } = await axios.get("http://localhost:8080/user/allPositions");
        console.log('职位分类',data)
        setCtgListValue(data.data);
      } catch (err) {
        console.log("err", err);
        // setCtgListValue([]);
        setCtgListValue([
          { category_id: "1", name: "Category 1" },
        ]);
      }
    }


  const workstation_onChange = (value) => {
    setworkstation_Value(value);
  };

  const city_onChange = (value) => {
    setarea_Value(""); // 清空第二个选单的选中值
    setDistrictOptions([]); // 清空第二个选单的选项
    setcity_Value(value);
    // console.log("city", value);
    // console.log("area", area_value);

    if (value != null) {
      setarea_Value(""); // 清空第二个选项的值
      setDistrictOptions([]); // 清空第二个选项的选项
      setArea_show(false); // 隐藏第二个选项

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

  const options = locationArr.map((item) => ({
    label: item.city,
    value: item.category_id,
  }));

  const workstation_options = workstationArr.map((item) => ({
    label: item.positionName,
    value: item.id,
  }));

  useEffect(() => {
    fetchCtgList();
    // console.log("newest_city", city_value);
    // console.log("newest_area", area_value);
  }, []);

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
        name="username"
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
          // type="password"
          prefixIcon={<LockOnIcon />}
          clearable
        />
      </FormItem>
      <FormItem
        label="职位"
        name="positionId"
        rules={[
          { required: true, message: "必选", type: "error" },
        ]}
      >
        {/* <Radio.Group>{categoryGroup}</Radio.Group> */}
        <Select
          value={workstation_value}
          onChange={workstation_onChange}
          style={{ width: "40%" }}
          clearable
          options={workstation_options}
        />
      </FormItem>
      <FormItem
        label="市"
        name="areaId"
        rules={[
          { required: true, message: "必选", type: "error" },
          // { min: 6, message: "至少需要6位", type: "error" },
        ]}
      >
        {/* <Radio.Group>{categoryGroup}</Radio.Group> */}
        <Select
          value={city_value}
          onChange={city_onChange}
          style={{ width: "40%" }}
          clearable
          options={options}
        />
      </FormItem>
      <FormItem
        label="区/县"
        name="area_category"
        rules={[
          { required: true, message: "必选", type: "error" },
          // { boolean: {area_value}, message: "重选", type: "error" },
          // { min: 6, message: "至少需要6位", type: "error" },
        ]}
      >
        {/* <Radio.Group>{categoryGroup}</Radio.Group> */}
        <Select
          // key={area_value} // 添加key属性
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
