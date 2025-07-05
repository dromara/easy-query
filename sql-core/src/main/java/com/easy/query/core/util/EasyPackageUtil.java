package com.easy.query.core.util;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * create time 2025/6/3 14:10
 * 包扫描工具类 - 递归扫描包下的所有类
 *
 * @author xuejiaming
 */
public class EasyPackageUtil {

    /**
     * 扫描包路径下的所有类
     * @param packageName 要扫描的包名 (例如: "com.example")
     * @return 包下所有类的完整类名集合 (包括子包)
     */
    public static Set<String> scanClasses(String packageName) {
        return scanClasses(packageName, true, true);
    }

    /**
     * 扫描包路径下的所有类
     * @param packageName 要扫描的包名 (例如: "com.example")
     * @param recursive 是否递归扫描子包
     * @param includeInnerClass 是否包含内部类
     * @return 包下所有类的完整类名集合
     */
    public static Set<String> scanClasses(String packageName, boolean recursive, boolean includeInnerClass) {
        Set<String> classNames = new LinkedHashSet<>();
        String packageDirName = packageName.replace('.', '/');

        try {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources(packageDirName);

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();

                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name());
                    scanClassesInFileSystem(packageName, filePath, recursive, includeInnerClass, classNames);
                } else if ("jar".equals(protocol)) {
                    scanClassesInJar(url, packageName, packageDirName, recursive, includeInnerClass, classNames);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Scan classes failed: " + e.getMessage(), e);
        }

        return classNames;
    }

    private static void scanClassesInFileSystem(String packageName, String packagePath,
                                                boolean recursive, boolean includeInnerClass, Set<String> classNames) {

        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles(file ->
                (recursive && file.isDirectory()) ||
                        (file.isFile() && file.getName().endsWith(".class"))
        );

        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanClassesInFileSystem(
                        packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        includeInnerClass,
                        classNames
                );
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                if (includeInnerClass || !isInnerClass(className)) {
                    classNames.add(packageName + '.' + className);
                }
            }
        }
    }

    private static void scanClassesInJar(URL jarUrl, String packageName, String packageDirName,
                                         boolean recursive, boolean includeInnerClass, Set<String> classNames) throws IOException {

        String jarPath = jarUrl.getPath();
        if (jarPath.startsWith("file:")) {
            jarPath = jarPath.substring(5, jarPath.indexOf("!"));
        }
        jarPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name());

        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                // 跳过目录和非.class文件
                if (entryName.charAt(0) == '/') {
                    entryName = entryName.substring(1);
                }
                if (!entryName.startsWith(packageDirName) ||
                        !entryName.endsWith(".class") ||
                        entry.isDirectory()) {
                    continue;
                }

                // 检查子包递归
                int lastSlash = entryName.lastIndexOf('/');
                String className = entryName.substring(lastSlash + 1, entryName.length() - 6);
                if (!recursive) {
                    String entryPackage = entryName.substring(0, lastSlash).replace('/', '.');
                    if (!packageName.equals(entryPackage)) {
                        continue;
                    }
                }

                if (includeInnerClass || !isInnerClass(className)) {
                    classNames.add(entryName
                            .replace('/', '.')
                            .replace('\\', '.')
                            .replace(".class", ""));
                }
            }
        }
    }

    private static boolean isInnerClass(String className) {
        return className.contains("$");
    }
}
