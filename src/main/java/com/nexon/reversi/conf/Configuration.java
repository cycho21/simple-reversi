package com.nexon.reversi.conf;

import com.nexon.reversi.Utils;
import org.json.simple.JSONObject;

/**
 * Created by chan8 on 2017-03-22.
 */
public class Configuration {
    public static Configuration configuration = null;
    public static final int BLANK_SPACE = 0;
    public static final int WHITE = 1;
    public static final int BLACK = -1;
    private int WIDTH;
    private int HEIGHT;

    private Configuration(Configurer configurer) {
        this.WIDTH = configurer.WIDTH;
        this.HEIGHT = configurer.HEIGHT;
    }

    public static Configuration getConfiguration() {
        if (configuration != null) {
            return configuration;
        } else {
            JSONObject jsonObject = Utils.parseConfiguration("./config.json");
            if (jsonObject == null) {
                configuration = new Configuration.Configurer().build();
                return configuration;
            }
            if (jsonObject.get("width") != null && jsonObject.get("height") != null)
                configuration = new Configuration.Configurer()
                        .setHeight(Integer.parseInt(String.valueOf(jsonObject.get("height"))))
                        .setWidth(Integer.parseInt(String.valueOf(jsonObject.get("width")))).build();
            else
                configuration = new Configuration.Configurer().build();
            return configuration;
        }
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public static class Configurer {
        private int WIDTH = 8;
        private int HEIGHT = 8;

        public Configurer setWidth(int width) {
            this.WIDTH = WIDTH;
            return this;
        }

        public Configurer setHeight(int height) {
            this.HEIGHT = height;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
