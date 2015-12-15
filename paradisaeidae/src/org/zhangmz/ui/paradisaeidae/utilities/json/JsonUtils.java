package org.zhangmz.ui.paradisaeidae.utilities.json;

import com.alibaba.fastjson.JSON;

/**
 * Json处理实用类
 * @author 张孟志
 * @date 2014-07-24
 */
public final class JsonUtils {
    /**
     * 构造函数
     */
    private JsonUtils() {
        // 不需要创建
    }

    /**
     * 对象转化为Json字符串
     * 
     * @param object
     *            对象
     * @return Json字符串
     */
    public static String obj2Json(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * Json字符串转化为Java对象
     * 
     * @param <T>
     *            泛型
     * @param clazz
     *            Java类
     * @param json
     *            Json字符串
     * @return 对象
     */
    public static <T> T json2Obj(Class<T> clazz, String json) {
        return JSON.parseObject(json, clazz);
    }

}
