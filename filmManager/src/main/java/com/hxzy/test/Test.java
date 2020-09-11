package com.hxzy.test;

import com.hxzy.bean.Film;
import com.hxzy.service.FilmService;
import com.hxzy.service.impl.FilmServiceImpl;
import com.hxzy.util.CommonUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/11
 */
public class Test {
    private static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN2 = "yyyy-MM-dd";
    private static final String PATTERN3 = "HH:mm:ss";
    private static final String PATTERN4 = "yyyy/MM/dd";

    public static void main(String[] args) {
        //test();
        //testSave();
        //testUpdate();
        //testRemove();
        testQueryAll();
    }

    private static void test() {
        LocalDateTime now = LocalDateTime.now();
        //得到当前的LocalDateTime的毫秒数
        long time = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        //根据毫秒实例化得到Date对象
        Date date = new Date(time);

        System.out.println(now);
        System.out.println(date);
    }

    private static void testSave(){
        //调用业务逻辑层
        FilmService service = new FilmServiceImpl();

        Film film = new Film();
        film.setChineseName("花木兰");
        film.setEnglishName("HuaMuLan");
        film.setArea("全球上映");
        film.setCategory("历史,动作");
        film.setDuration(120);

        //将一个字符串转换为LocalDateTime
        //字符串转时间
        String dateTimeStr = "2020-09-10 12:10:15";// yyyyMMddHHmmss   20200911100312
        LocalDateTime localDateTime = CommonUtil.str2LocalDateTime(dateTimeStr,PATTERN1);

        film.setRelease(localDateTime);
        int row = service.save(film);
        System.out.println(row > 0 ? "插入成功" : "插入失败");

    }

    public static void testUpdate(){
        //调用业务逻辑层
        FilmService service = new FilmServiceImpl();

        Film film = new Film();
        film.setfId(2);
        film.setChineseName("飓风营救");
        film.setEnglishName("jufengyingjiu");
        film.setArea("全球上映");
        film.setCategory("动作,悬疑");
        film.setDuration(220);
        String dateTimeStr = "2018-08-10 12:10:15";
        LocalDateTime localDateTime = CommonUtil.str2LocalDateTime(dateTimeStr,PATTERN1);
        film.setRelease(localDateTime);

        service.update(film);
    }

    public static void testRemove(){
        //调用业务逻辑层
        FilmService service = new FilmServiceImpl();
        int remove = service.remove(3);
        System.out.println(remove > 0);
    }

    public static void testQueryAll(){
        //调用业务逻辑层
        FilmService service = new FilmServiceImpl();
        List<Film> films = service.queryAll();
        films.stream().forEach(System.out::println);
    }
}
