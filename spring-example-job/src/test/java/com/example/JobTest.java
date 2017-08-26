package com.example;

import org.junit.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class JobTest {

    @Test
    public void test() throws Exception {
        doAAA();
        doBBB();
    }

    private void doAAA () throws Exception {
        ClassLoader parentLoader = this.getClass().getClassLoader();
        URL jar = getJar("job-example-aaa-0.0.1.jar");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, parentLoader, null);
        Class<?> clazz = classLoader.loadClass("com.example.MyJob");
        Object object = clazz.newInstance();
        Method method = object.getClass().getDeclaredMethod("say");
        method.invoke(object);
        classLoader.close();
    }

    private void doBBB () throws Exception {
        ClassLoader parentLoader = this.getClass().getClassLoader();
        URL jar = getJar("job-example-bbb-0.0.1.jar");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jar}, parentLoader, null);
        Class<?> clazz = classLoader.loadClass("com.example.MyJob");
        Object object = clazz.newInstance();
        Method method = object.getClass().getDeclaredMethod("say");
        method.invoke(object);
        classLoader.close();
    }

    private URL getJar(String jarName) {
        URL url = getClass().getClassLoader().getResource(jarName);
        if (url != null)
            return url;
        return null;
    }
}
