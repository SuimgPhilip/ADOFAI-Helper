package cn.suimg.adofai.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import static com.alibaba.fastjson.serializer.SerializerFeature.*;

import java.util.List;

/**
 * JSON序列化工具
 */
public class JSONUtil {

    public static SerializerFeature[] filters = {
//            WriteNullListAsEmpty,
//            WriteNullStringAsEmpty,
            PrettyFormat,
//            MapSortField,
    };

    /**
     * 序列化对象为json
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        return JSONObject.toJSONString(obj,filters);
    }

    /**
     * 序列化对象为json byte
     *
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object) {
        return JSONObject.toJSONBytes(object,filters);
    }

    /**
     * 反序列化JSON
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 反序列化List
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toListObject(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
