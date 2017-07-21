package com.yanxuwen.retrofit.Annotation.reflex;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：严旭文 on 2016/8/2 17:24
 * 邮箱：420255048@qq.com
 * 反射的一些工具
 */
public class ReflectionUtil {
    private static final String TYPE_NAME_PREFIX_CLASS = "class ";
    private static final String TYPE_NAME_PREFIX_INTERFACE = "interface ";
    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX_CLASS)) {
            className = className.substring(TYPE_NAME_PREFIX_CLASS.length());
        }else  if (className.startsWith(TYPE_NAME_PREFIX_INTERFACE)) {
            className = className.substring(TYPE_NAME_PREFIX_INTERFACE.length());
        }
        return className;
    }

    /**
     * Type获取class
     * @param type
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> getClass(Type type)
            throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    /**
     * 通过Type创建对象
     */
    public static Object newInstance(Type type)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = getClass(type);
        if (clazz==null) {
            return null;
        }
        return clazz.newInstance();
    }
    /**
     * 利用反射获取运行时泛型参数的类型，并数组的方式返回。本例中为返回一个T类型的Type数组。
     */
    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }

        return ((ParameterizedType)superclassType).getActualTypeArguments();
    }

    /**
     * 检查对象是否存在默认构造函数
     */
    public static boolean hasDefaultConstructor(Class<?> clazz) throws SecurityException {
        Class<?>[] empty = {};
        try {
            clazz.getConstructor(empty);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取指定类型中的特定field类型
     */
    public static Class<?> getFieldClass(Class<?> clazz, String name) {
        if (clazz==null || name==null || name.isEmpty()) {
            return null;
        }

        Class<?> propertyClass = null;

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equalsIgnoreCase(name)) {
                propertyClass = field.getType();
                break;
            }
        }

        return propertyClass;
    }

    /**
     * 获取指定类型中的特定method返回类型
     */
    public static Class<?> getMethodReturnType(Class<?> clazz, String name) {
        if (clazz==null || name==null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase();
        Class<?> returnType = null;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                returnType = method.getReturnType();
                break;
            }
        }

        return returnType;
    }

    /**
     * 根据字符串标示获取枚举常量
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object getEnumConstant(Class<?> clazz, String name) {
        if (clazz==null || name==null || name.isEmpty()) {
            return null;
        }
        return Enum.valueOf((Class<Enum>)clazz, name);
    }
}
