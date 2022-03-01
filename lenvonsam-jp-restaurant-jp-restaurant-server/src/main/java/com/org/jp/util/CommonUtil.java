package com.org.jp.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.core.io.ClassPathResource;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通用工具类
 *
 * @author samy
 */
public class CommonUtil {
    /**
     * 集团默认编号
     */
    public static String DEFAULT_PLATFORM_CODE = "JPRT";

    public static File getTempImgFile() throws Exception {
        ClassPathResource resource = new ClassPathResource("temp/tempImg");
        return resource.getFile();
    }

    public static ByteArrayOutputStream compressImgFile(InputStream inputStream, String imgFileType) throws Exception {
//        ClassPathResource res = new ClassPathResource("temp/tempImg");
//        InputStream tempStream = res.getInputStream();
        byte[] bytes = new byte[0];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(bytes);
        out.flush();
//        ByteArrayOutputStream out = new ByteArrayOutputStream(bytes);
//        FileOutputStream fileOutputStream = new FileOutputStream(bytes);
        String type = "jpg";
        if ("image/png".equals(imgFileType)) {
            type = "png";
        }
        Thumbnails.of(inputStream).scale(1.0).outputFormat(type).outputQuality(.5).toOutputStream(out);
        return out;
    }
    
    public static Map<String, Map<String, Object>> compareObject(Object oldObject, Object newObject) {
        return compareObject(oldObject, newObject, null);
    }

    public static Map<String, Map<String, Object>> compareObject(Object oldObject, Object newObject, List<String> excludeFields) {
        if ((Objects.isNull(excludeFields) || (excludeFields.size() == 0)) && Objects.deepEquals(oldObject, newObject)) {
            return null;
        } else {
            Map<String, Map<String, Object>> result = new HashMap<>();
            try {
                PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(oldObject.getClass()).getPropertyDescriptors();
                for (PropertyDescriptor pdr : propertyDescriptors) {
                    String fieldName = pdr.getName();
                    if (Objects.nonNull(excludeFields) && excludeFields.contains(fieldName)) {
                        continue;
                    }
                    Method readMethod = pdr.getReadMethod();
                    Object oldValue = readMethod.invoke(oldObject);
                    Object newValue = readMethod.invoke(newObject);
                    if (!Objects.deepEquals(oldValue, newValue)) {
                        Map<String, Object> temp = new HashMap<>();
                        temp.put("old", oldValue);
                        temp.put("new", newValue);
                        result.put(fieldName, temp);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 处理内存溢出
                CachedIntrospectionResults.clearClassLoader(ClassLoader.getSystemClassLoader());
            }
            return result.size() > 0 ? result : null;
        }
    }
}
