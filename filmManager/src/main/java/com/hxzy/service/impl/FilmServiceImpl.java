package com.hxzy.service.impl;

import com.hxzy.bean.Film;
import com.hxzy.dao.FilmDao;
import com.hxzy.dao.impl.FilmDaoImpl;
import com.hxzy.service.FilmService;

import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/11
 */
public class FilmServiceImpl implements FilmService {
    //业务逻辑层调用数据访问层
    private FilmDao dao = new FilmDaoImpl();

    @Override
    public int save(Film film) {
        return dao.save(film);
    }

    @Override
    public int update(Film film) {
        return dao.update(film);
    }

    @Override
    public int remove(Integer integer) {
        return dao.remove(integer);
    }

    @Override
    public List<Film> queryAll() {
        return dao.queryAll();
    }

    @Override
    public Film findById(Integer integer) {
        return dao.findById(integer);
    }
}
