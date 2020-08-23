package com.yunda.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Arouter {
    //详细路由路径必填，如"/app/MainActivity"
    String path();
    //从path中截取，规范开发者的编码
    String group() default "";
}
