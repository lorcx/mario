package com.lx.mario.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by lx on 2017/5/21.
 */
public class ConfigLoader {
    private Map<String, Object> configMap;

    public ConfigLoader() {
        this.configMap = new HashMap<>();
    }

    /**
     * 加载配置
     * @param conf
     */
    public void load(String conf) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(conf));
            toMap(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toMap(Properties properties) {
        if (null != properties) {
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
                Object value = properties.get(key.toString());
                configMap.put(key.toString(), value);
            }
        }
    }

    public String getConf(String name) {
        Object value = configMap.get(name);
        if (null != value) {
            return value.toString();
        }
        return null;
    }

    public void setConf(String name, String value) {
        configMap.put(name, value);
    }

    public Object getObject(String name) {
        return configMap.get(name);
    }
}
