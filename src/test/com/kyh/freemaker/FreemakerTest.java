package com.kyh.freemaker;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.Configuration;
import freemarker.template.Template;

import com.alibaba.fastjson.JSON;
import com.kyh.BaseTest;

/**
 * @author kongyunhui on 2018/8/7.
 */
public class FreemakerTest extends BaseTest {
    @Autowired
    private Configuration freemaker;

    @Test
    public void testFreemaker() throws Exception {
        //指定输出文件
        File file = new File("/tmp/nginx/nginx.conf");
        if (file.getParentFile().exists() == false) {
            FileUtils.forceMkdir(file.getParentFile());
        }
        //备份
        backup(file);
        //输出
        Template template = freemaker.getTemplate("nginx.tpl");
        template.process(generateParams(), new FileWriter(file));
    }

    /**
     * 对配置文件进行备份
     *
     * @param file
     */
    private void backup(File file) {
        try {
            if (file.exists()) {
                File dest = new File("/tmp/nginx/nginx.conf" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
                FileUtils.moveFile(file, dest);
            }
        } catch (IOException e) {
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e1) {
                e.printStackTrace();
            }
        }
        // 清理过期数据,保留最近一周的数据
        long checkPoint = new Date().getTime() - 7 * 24 * 60 * 60 * 1000;

        File[] expiredFiles = new File("tmp/nginx/").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.lastModified() < checkPoint) {
                    return true;
                }
                return false;
            }
        });
        if (expiredFiles != null) {
            for (File expiredFile : expiredFiles) {
                try {
                    FileUtils.forceDelete(expiredFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成tpl动态替换数据
     *
     * @return
     */
    private Map<String, Object> generateParams() {
        String json = "{\n" +
                "\t\"http\":{\n" +
                "\t\t\"servers\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"listen_port\":80,\n" +
                "\t\t\t\t\"server_name\":\"_\",\n" +
                "\t\t\t\t\"locations\":[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"path\":\"/\",\n" +
                "\t\t\t\t\t\t\"proxy_pass\":\"app1\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"listen_port\":81,\n" +
                "\t\t\t\t\"server_name\":\"_\",\n" +
                "\t\t\t\t\"locations\":[\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"path\":\"/\",\n" +
                "\t\t\t\t\t\t\"proxy_pass\":\"\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";
        return JSON.parseObject(json, HashMap.class);
    }
}
