package com.easy.query.core.util;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
     * 扫描包路径下的所有类（优化版）
     * @param packageName 要扫描的包名 (例如: "com.example")
     * @return 包下所有类的完整类名集合 (包括子包)
     */
    public static Set<String> scanClasses(String packageName) {
        return scanClasses(packageName, true, true);
    }

    /**
     * 扫描包路径下的所有类（优化版）
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
                    Path dirPath = Paths.get(url.toURI());
                    scanClassesInFileSystem(packageName, dirPath, recursive, includeInnerClass, classNames);
                } else if ("jar".equals(protocol)) {
                    scanClassesInJar(url, packageName, packageDirName, recursive, includeInnerClass, classNames);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Scan classes failed: " + e.getMessage(), e);
        }

        return classNames;
    }

    // 使用NIO2的FileVisitor迭代代替递归（避免栈溢出，提升大目录性能）
    private static void scanClassesInFileSystem(String packageName, Path dirPath,
                                                boolean recursive, boolean includeInnerClass, Set<String> classNames) throws IOException {
        if (!Files.isDirectory(dirPath)) return;

        PathMatcher classMatcher = FileSystems.getDefault().getPathMatcher("glob:**.class");
        Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (classMatcher.matches(file)) {
                    String className = toClassName(packageName, dirPath.relativize(file));
                    if (includeInnerClass || !isInnerClass(className)) {
                        classNames.add(className);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // 检查是否继续遍历子目录
                if (recursive || dir.equals(dirPath)) {
                    return FileVisitResult.CONTINUE;
                } else {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }
        });
    }

    // JAR扫描优化：减少字符串操作和重复解码
    private static void scanClassesInJar(URL jarUrl, String packageName, String packageDirName,
                                         boolean recursive, boolean includeInnerClass, Set<String> classNames) throws IOException {
        JarURLConnection jarConn = (JarURLConnection) jarUrl.openConnection();
        try (JarFile jar = jarConn.getJarFile()) {
            Enumeration<JarEntry> entries = jar.entries();
            String prefix = packageDirName + "/"; // 预构建匹配前缀
            int prefixLength = prefix.length();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                // 快速跳过非目标条目
                if (entry.isDirectory() || !entryName.endsWith(".class") || !entryName.startsWith(prefix)) {
                    continue;
                }

                // 递归检查（避免字符串分割）
                if (!recursive) {
                    int endIndex = entryName.lastIndexOf('/');
                    String entryPackage = entryName.substring(0, endIndex).replace('/', '.');
                    if (!packageName.equals(entryPackage)) continue;
                }

                // 高效提取类名（复用原始字符串）
                String className = entryName.substring(prefixLength, entryName.length() - 6)
                        .replace('/', '.')
                        .replace('\\', '.');
                if (includeInnerClass || !isInnerClass(className)) {
                    classNames.add(packageName + '.' + className);
                }
            }
        }
    }

    // 辅助方法：路径转类名
    private static String toClassName(String basePackage, Path relativePath) {
        String path = relativePath.toString().replace(FileSystems.getDefault().getSeparator(), ".");
        return basePackage + '.' + path.substring(0, path.length() - 6); // 移除".class"
    }

    private static boolean isInnerClass(String className) {
        return className.indexOf('$') >= 0; // 比contains()稍快
    }
}
