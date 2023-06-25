package com.easy.query.processor;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Properties;

/**
 * create time 2023/6/24 14:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryProxyProperties {
    private static final String DEFAULT_ENCODING = "UTF-8";
    protected Properties properties = new Properties();

    public EasyQueryProxyProperties(Filer filer) {
        InputStream inputStream = null;
        try {
            FileObject propertiesFileObject = filer.getResource(StandardLocation.CLASS_OUTPUT, ""
                    , "easy-query-proxy.properties");

            File propertiesFile = new File(propertiesFileObject.toUri());
            if (propertiesFile.exists()) {
                inputStream = propertiesFileObject.openInputStream();
            } else if (getClass().getClassLoader().getResource("easy-query-proxy.properties") != null) {
                inputStream = getClass().getClassLoader().getResourceAsStream("easy-query-proxy.properties");
            } else {
                File pomXmlFile = new File(propertiesFile.getParentFile().getParentFile().getParentFile(), "pom.xml");
                if (pomXmlFile.exists()) {
                    propertiesFile = new File(pomXmlFile.getParentFile(), "src/main/resources/easy-query-proxy.properties");
                }
            }

            if (inputStream == null && propertiesFile.exists()) {
                inputStream = Files.newInputStream(propertiesFile.toPath());
            }

            if (inputStream != null) {
                properties.load(new InputStreamReader(inputStream, DEFAULT_ENCODING));
            }
        } catch (Exception ignored) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
