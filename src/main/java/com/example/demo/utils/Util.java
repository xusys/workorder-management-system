package com.example.demo.utils;

import java.lang.reflect.Method;
import java.util.*;

public class Util {
    public static List<Map<String, Object>> activitiResult(List<?> objs) {
        // 用于存放多个对象的集合
        List<Map<String, Object>> pdResult = new ArrayList<>();
        // 遍历方法参数中的集合
        for (Object obj : objs) {
            // 用于封装单个对象 get 方法返回值的 Map 集合
            Map<String, Object> pdMap = new HashMap<>();
            // 通过反射获取该对象的方法对象数组
            Method[] methods = obj.getClass().getMethods();
            // 遍历方法对象数组
            for (Method method : methods) {
                // 获取方法名称
                String methodName = method.getName();
                // 判断该方法是否名称不为 null ，并且名称是以 get 开头，满足条件进入 if 中
                if (methodName != null && methodName.startsWith("get")) {
                    // 设置方法的访问权限
                    method.setAccessible(true);
                    try {
                        // 将方法名的 get 前缀去掉，并增加 pd 前缀
                        String pdKey = "".concat(methodName.substring(3));
                        // 将 get 方法的名称作为 Map 的 key，将返回值作为 value 进行封装
                        pdMap.put(pdKey, method.invoke(obj, null));
                    } catch (Exception e) {
                        //输出异常,由于使得后台过乱，所以关掉
//                        e.printStackTrace();
                    }
                }
            }
            // 将封装好的 Map 集合添加到 List 集合中
            pdResult.add(pdMap);
        }
        return pdResult;
    }
}
