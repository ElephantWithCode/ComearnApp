package com.example.team.commonlibrary.base.util;

import java.util.HashMap;

/**
 * Created by 邹特强 on 2018/4/2.
 * 利用builder设计模式快速生成Map的工具类
 * 目前只是为了方便增加数据
 * TODO:这里改成了HashMap<String,Object>不知道会不会出问题
 * @author 邹特强
 */

public class MapGenerator extends HashMap<String,Object> {
    public static MapGenerator generate(){
        return new MapGenerator();
    }
    public MapGenerator add(String key, Object value) {
        this.put(key,value);
        return this;
    }
}
