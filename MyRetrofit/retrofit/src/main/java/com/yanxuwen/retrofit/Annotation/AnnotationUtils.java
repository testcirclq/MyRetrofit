package com.yanxuwen.retrofit.Annotation;

import com.yanxuwen.retrofit.api.ApiManager;
import com.yanxuwen.retrofit.Annotation.reflex.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：严旭文 on 2016/8/2 16:37
 * 邮箱：420255048@qq.com
 */
public class AnnotationUtils {
    /********************************获取注解Description内容****************************************/
    /**
     *获取注解Description内容
     */
    public static String getAnnotationDescription(Type t){
        try {
            return getAnnotationDescription(ReflectionUtil.getClass(t).getAnnotations());
        }catch (ClassNotFoundException e){}
       return "";
    }
    /**
     *获取注解Description内容
     */
    public static String getAnnotationDescription(Object object){
        return getAnnotationDescription(object.getClass().getAnnotations());
    }
    /**
     * 获取注解Description内容
     */
    public static String getAnnotationDescription(Annotation[] classAnnotation){
        for(Annotation cAnnotation : classAnnotation){
            Class annotationType =  cAnnotation.annotationType();
           if(annotationType.getName().equals(com.yanxuwen.retrofit.Annotation.Description.class.getName())) {
               com.yanxuwen.retrofit.Annotation.Description mDescription=(Description)cAnnotation;
               return mDescription.value();
           }
        }
        return "";
    }
    /************************************************************************/
    /********************************获取注解HttpType内容****************************************/
    /**
     *获取注解HttpType内容
     */
    public static  ApiManager.HttpType getAnnotationHttpType(Type t){
        try {
            return getAnnotationHttpType(ReflectionUtil.getClass(t).getAnnotations());
        }catch (ClassNotFoundException e){}
        return null;
    }
    /**
     *获取注解HttpType内容
     */
    public static  ApiManager.HttpType getAnnotationHttpType(Object object){
        return getAnnotationHttpType(object.getClass().getAnnotations());
    }
    /**
     * 获取注解HttpType内容
     */
    public static  ApiManager.HttpType getAnnotationHttpType(Annotation[] classAnnotation){
        for(Annotation cAnnotation : classAnnotation){
            Class annotationType =  cAnnotation.annotationType();
            if(annotationType.getName().equals(com.yanxuwen.retrofit.Annotation.HttpType.class.getName())) {
                com.yanxuwen.retrofit.Annotation.HttpType mHttpType=(HttpType)cAnnotation;
                return mHttpType.value();
            }
        }
        return null;
    }
    /************************************************************************/
    /**
     * @param object              有注解的类,格式必须是对象，不能为xxx.class
     * @param annotationType     注解的类如xxx.class
     * @return
     * 获取改类某个注解的所有的字段
     */
    public static List<Field>  getAnnotationField(Object object,Class<? extends Annotation> annotationType){
        Class<?> clazz = object.getClass();
        return getAnnotationField(clazz,annotationType);
    }
    /**
     * @param clazz
     * @param annotationType     注解的类如xxx.class
     * @return
     * 获取改类某个注解的所有的字段
     */
    public static List<Field>  getAnnotationField(Class<?> clazz,Class<? extends Annotation> annotationType){
        Field[] fields = clazz.getDeclaredFields();//获得Activity中声明的字段
        List<Field> list_field=new ArrayList<>();
        for (Field field : fields) {
            // 查看这个字段是否有我们自定义的注解类标志的
            if (field.isAnnotationPresent(annotationType)) {
                list_field.add(field);
            }
        }
        return  list_field;
    }

    /**
     * @param   type          有注解的类格式为xxx.class
     * @param annotationType  注解的类如xxx.class
     * @param field           查找的字段
     * @return
     */
    public static Object getAnnotationFieldValue(Type type,Class<? extends Annotation> annotationType,String field){
        try {
            List<Field> list_field=getAnnotationField(ReflectionUtil.getClass(type),annotationType);
            for(int i=0;i<list_field.size();i++){
                Field mField=list_field.get(i);
                if(mField.getName().equals(field)){
                    return  list_field.get(i).get(type);
                }
            }
        }catch (ClassNotFoundException e){

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
