import "./ticketList.css";
import axios from "axios";
import { useState, useEffect } from "react";
import { Input, Select, DatePicker, Button, DialogPlugin } from "tdesign-react";
import TicketTable from "../ticketTable/ticketTable";
const { Option } = Select;

// 渲染工单列表和查询表单的整个界面
export default function TicketList(props) {
  const selectList = [
    { type: "input", title: "工单标题：", placeholder: "请输入工单标题" },
    { type: "date", title: "创建时间：" },
  ];
  if (props.page !== "distribute" && props.page !== "todo") {
    selectList.splice(1, 0, {
      type: "select",
      title: "是否结束：",
      placeholder: "请选择状态",
    });
  }

  const { pageParmas } = props;
  const { datalist } = props;

  const [queryParams, setQueryParams] = useState(pageParmas);

  const [inputValue, setInputChange] = useState();
  const onInputChange = (value) => {
    setInputChange(value);
  };
  const [selectValue, setSelectValue] = useState();

  const onSelectChange = (value) => {
    // console.log(value);
    setSelectValue(value);
    // console.log("selectValue",selectValue);
  };

  const [dateValue, setDateValue] = useState();
  const onDateChange = (value) => {
    setDateValue(value);
  };

  // 处理提交查询的函数。根据页面类型构建不同的查询参数对象，
  // 并更新 queryParams 状态
  const submit = () => {
    if (props.page !== "distribute" || props.page !== "todo") {
      setQueryParams(
        Object.assign({}, queryParams, {
          title: inputValue,
          status: selectValue,
          time_scope: dateValue && dateValue.length ? dateValue : null,
        })
      );
    } else {
      setQueryParams(
        Object.assign({}, queryParams, {
          title: inputValue,
          time_scope: dateValue && dateValue.length ? dateValue : null,
        })
      );
    }
  };

  // 根据配置项的类型，选择不同的表单组件进行渲染
  const listItems = selectList.map((item, index) => {
    let selectType;
    if (item.type === "input") {
      selectType = (
        <Input
          placeholder={item.placeholder}
          onChange={onInputChange}
          clearable
        />
      );
    } else if (item.type === "select") {
      selectType = (
        <div style={{ display: "flex", flex: "1 1" }}>
          <Select value={selectValue} onChange={onSelectChange} clearable>
            <Option key="true" label="是" value="2" />
            <Option key="false" label="否" value="1" />
          </Select>
        </div>
      );
    } else if (item.type === "date") {
      selectType = (
        <DatePicker
          clearable
          mode="date"
          range
          onChange={onDateChange}
          style={{ flex: "1 1" }}
        ></DatePicker>
      );
    }
    return (
      <div
        key={index}
        className="ti-input-wapper"
        style={{ width: item.width }}
      >
        <span className="ti-input-title">{item.title}</span>
        {selectType}
      </div>
    );
  });

  useEffect(() => {
    // console.log("selectValue", selectValue);
  }, [selectValue]);

  
  return (
    <section
      className="ti-section-wrapper ti-related-wrapper"
      style={{ height: "100%" }}
    >
      <div className="ti-section-content">
        <div className="ti-input-area">
          <div className="ti-input-container">{listItems}</div>
          <div className="ti-input-operation-container">
            <Button onClick={submit}>查询</Button>
          </div>
        </div>
        <div className="ti-table-area">
          {/* 接受查询参数作为 props，并用于显示符合查询条件的工单列表 */}
        <TicketTable queryParams={queryParams} datalist={datalist} selectValue={selectValue}></TicketTable>
        </div>
      </div>
    </section>
  );
}
