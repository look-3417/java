package com.hxzy.dao.impl;

import com.hxzy.bean.Film;
import com.hxzy.dao.FilmDao;
import com.hxzy.util.CommonUtil;
import com.hxzy.util.JdbcUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public class FilmDaoImpl implements FilmDao {
    @Override
    public int save(Film film) {
        String sql = "INSERT INTO film (chinese_name,english_name,category,area,duration,`release`) VALUES(?,?,?,?,?,?)";

        Object[] params = {
                film.getChineseName(),
                film.getEnglishName(),
                film.getCategory(),
                film.getArea(),
                film.getDuration(),
                //将LocalDateTime对象转换为TimeStamp对象,jdbc中的timestamp类型对应数据库中的datetime类型
                CommonUtil.localDateTime2TimeStamp(film.getRelease())
        };
        return JdbcUtils.executeUpdate(sql,params);
    }

    @Override
    public int update(Film film) {
        String sql = "UPDATE film SET chinese_name=?,english_name=?,category=?,area=?,duration=?,`release`=? WHERE f_id=?";
        Object[] params = {
                film.getChineseName(),
                film.getEnglishName(),
                film.getCategory(),
                film.getArea(),
                film.getDuration(),
                //将LocalDateTime对象转换为TimeStamp对象
                CommonUtil.localDateTime2TimeStamp(film.getRelease()),
                film.getfId()
        };
        return JdbcUtils.executeUpdate(sql,params);
    }

    @Override
    public int remove(Integer integer) {
        String sql = "delete from film where f_id = ?";
        return JdbcUtils.executeUpdate(sql,integer);
    }

    @Override
    public List<Film> queryAll() {
        String sql = "SELECT f_id as fId, chinese_name as chineseName,english_name as englishName,category,area,duration,`release` from film";
        List<Map<String, Object>> maps = JdbcUtils.executeQuery(sql);

        return maps.stream().map(t -> {
            int id = ((Long)t.get("fId")).intValue();
            String chineseName = (String) t.get("chineseName");
            String englishName = (String) t.get("englishName");
            String category = (String) t.get("category");
            String area = (String) t.get("area");
            double duration = (double) t.get("duration");
            //数据库中日期时间用datetime，对应的jdbc类型为Timestamp
            Timestamp release = (Timestamp) t.get("release");
            LocalDateTime localDateTime = CommonUtil.timeStamp2LocalDateTime(release);

            /*System.out.println("fid:" + t.get("fId").getClass().getName() +"=====>" + id);
            System.out.println("chineseName:" + t.get("chineseName").getClass().getName());
            System.out.println("englishName:" + t.get("englishName").getClass().getName());
            System.out.println("category:" + t.get("category").getClass().getName());
            System.out.println("area:" + t.get("area").getClass().getName());
            System.out.println("duration:" + t.get("duration").getClass().getName());
            System.out.println("release:" + t.get("release").getClass().getName());
            System.out.println("\n\n");*/

            Film film = new Film();
            //为film对象属性赋值
            film.setfId(id);
            film.setChineseName(chineseName);
            film.setEnglishName(englishName);
            film.setCategory(category);
            film.setArea(area);
            film.setDuration(duration);
            film.setRelease(localDateTime);
            return film;
        }).collect(Collectors.toList());
    }

    @Override
    public Film findById(Integer integer) {
        return null;
    }
}
