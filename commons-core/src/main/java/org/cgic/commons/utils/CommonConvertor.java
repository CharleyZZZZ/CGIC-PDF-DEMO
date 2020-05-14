package org.cgic.commons.utils;

import org.cgic.commons.exception.BaseException;
import org.cgic.commons.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 通用转换工具
 * @Author: charleyZZZZ
 * @Date: 2020/4/13 10:47
 * @Version 1.0
 */
public class CommonConvertor {


    /**
     * javaBean对象转换
     *
     * @param targetClazz
     * @param sourceObject
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> T beanConvert(Class<T> targetClazz, V sourceObject) throws CommonException{
        Assert.notNull(sourceObject, "source convert object can't be null.");
        try {
            T targetObject = targetClazz.newInstance();
            BeanUtils.copyProperties(sourceObject, targetObject);
            return targetObject;
        } catch (Exception e) {
            throw new CommonException("convert to {} error:", targetClazz.getName());
        }
    }


//    /**
//     * List对象转换
//     *
//     * @param targetClazz 目标对象class
//     * @param sourceList 源对象list
//     * @param <T>
//     * @param <V>
//     * @return
//     */
//    public static <T, V> List<T> listConvertor(Class<T> targetClazz, List<V> sourceList) {
//        return listConvertor(targetClazz, sourceList, null);
//    }
//
//    /**
//     *
//     * List对象转换
//     * @param targetClazz
//     * @param sourceList
//     * @param rule 自定义映射规则(target字段,source字段)
//     * @return
//     */
//    public static <T, V> List<T> listConvertor(Class<T> targetClazz, List<V> sourceList, Map<String, String> rule) {
//        Assert.notNull(sourceList, "source convert list can't be null.");
//        List<T> targetObjects = new ArrayList<T>();
//        sourceList.forEach(sourceObject -> {
//            try {
//                T target = targetClazz.newInstance();
//                BeanUtils.copyProperties(sourceObject, target);
//                // 复制自定义映射
//                if (rule != null) {
//                    rule.forEach((targetField, sourceField) -> {
//                        try {
//                            Field sourceObjField = sourceObject.getClass().getDeclaredField(sourceField);
//                            sourceObjField.setAccessible(true);
//                            Object value = sourceObjField.get(sourceObject);
//                            Field targetObjField = target.getClass().getDeclaredField(targetField);
//                            targetObjField.setAccessible(true);
//                            targetObjField.set(target, value);
//                        } catch (Exception e) {
//                            throw new CommonException("convert field error:", e, targetField);
//                        }
//                    });
//                }
//                targetObjects.add(target);
//            } catch (Exception e) {
//                throw new CommonException("convert to {} error:", e, targetClazz.getName());
//            }
//        });
//        return targetObjects;
//    }
//
//    /**
//     * List对象转换(过滤当前多语言)
//     * @param targetClazz
//     * @param field 多语言字段名
//     * @param sourceList
//     * @return
//     */
//    public static <T, V> List<T> listConvertor(Class<T> targetClazz, String field, List<V> sourceList) {
//        return listConvertor(targetClazz, field, sourceList, null);
//    }
//
//    /**
//     *
//     * description
//     * @param targetClazz
//     * @param field 多语言字段名
//     * @param sourceList
//     * @param rule 自定义映射规则(target字段,source字段)
//     * @return
//     */
//    public static <T, V> List<T> listConvertor(Class<T> targetClazz, String field, List<V> sourceList,
//                                               Map<String, String> rule) {
//        Assert.notNull(sourceList, "source convert list can't be null.");
//        List<T> targetObjects = new ArrayList<T>();
//        sourceList.forEach(sourceObject -> {
//            try {
//                Field langField = sourceObject.getClass().getDeclaredField(field);
//                langField.setAccessible(true);
//                if (!ObjectUtils.equals(DetailsHelper.getUserDetails().getLanguage(), langField.get(sourceObject))) {
//                    T target = targetClazz.newInstance();
//                    BeanUtils.copyProperties(sourceObject, target);
//                    // 复制自定义映射
//                    if (rule != null) {
//                        rule.forEach((targetField, sourceField) -> {
//                            try {
//                                Field sourceObjField = sourceObject.getClass().getDeclaredField(sourceField);
//                                sourceObjField.setAccessible(true);
//                                Object value = sourceObjField.get(sourceObject);
//                                Field targetObjField = target.getClass().getDeclaredField(targetField);
//                                targetObjField.setAccessible(true);
//                                targetObjField.set(target, value);
//                            } catch (Exception e) {
//                                throw new CommonException("convert field error:", e, targetField);
//                            }
//                        });
//                    }
//                    targetObjects.add(target);
//                }
//            } catch (Exception e) {
//                throw new CommonException("convert to {} error:", e, targetClazz.getName());
//            }
//        });
//        return targetObjects;
//    }



}
