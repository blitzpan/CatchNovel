package com.novel.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.novel.entity.Tianya;

@Repository
public class TianyaDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Tianya> queryAll(Map para) throws Exception{
		String sql = "SELECT * from tianya";
		Object[] values = new Object[]{};
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Tianya.class), values);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
