package io.mo.gendata.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Map;

public class YamlUtil {
    private Map info;

    public Map getInfo(String filename) throws FileNotFoundException,UnsupportedEncodingException {
        Yaml yaml = new Yaml();
        URL url = YamlUtil.class.getClassLoader().getResource(filename);

        if (url != null) {
            this.info = (Map) yaml.load(new FileInputStream(URLDecoder.decode(url.getFile(), "utf-8")));
            return info;
        }
        return null;
    }
}
