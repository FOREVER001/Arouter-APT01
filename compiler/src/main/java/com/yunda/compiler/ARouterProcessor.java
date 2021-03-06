package com.yunda.compiler;

import com.google.auto.service.AutoService;
import com.yunda.annotation.Arouter;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.yunda.annotation.Arouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {
    //操作Element工具类
    private Elements elementUtils;
    //type（类信息）工具类
    private Types typeUtils;
   //用来输出错误，警告的日志
    private Messager mMessager;
   //文件生成器
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils=processingEnvironment.getElementUtils();
        typeUtils=processingEnvironment.getTypeUtils();
        mMessager=processingEnvironment.getMessager();
        mFiler=processingEnvironment.getFiler();

        String content = processingEnvironment.getOptions().get("content");
        mMessager.printMessage(Diagnostic.Kind.NOTE,"==content:=="+content);

    }


    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成java文件
     * @param set 使用了支持处理注解的节点集合
     * @param roundEnvironment 当前或是之前的运行环境，可以通过该对象查找找到的注解
     * @return true 表示后续处理器不会在处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
       if(set.isEmpty())return false;
       //获取项目中所有使用Arouter注解的节点
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Arouter.class);
        //遍历所以的类节点
        for (Element element : elements) {
            //类节点之上就是包节点
            String packageName=elementUtils.getPackageOf(element).getQualifiedName().toString();
            //获取简单类名
            String className=element.getSimpleName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE,"被注解的类：== "+className);
            //最终我们想要生成的类文件：如MainActivity$$ARouter
            String finalClassName=className+"$$ARouter";
            try {
                JavaFileObject sourceFile= mFiler.createSourceFile(packageName+"."+finalClassName);
                Writer writer = sourceFile.openWriter();

                //设置包名
                writer.write("package "+packageName+";\n");
                writer.write("public class "+finalClassName +"{\n");
                writer.write("public static Class<?> findTargetClass(String path) {\n");
                //获取类之上@Arouter注解的path值
                Arouter arouter = element.getAnnotation(Arouter.class);
                writer.write("if (path.equalsIgnoreCase(\""+arouter.path()+"\")){\n");
                writer.write("return "+className+".class;\n}\n");
                writer.write("return null;\n");
                writer.write("}\n}");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}
