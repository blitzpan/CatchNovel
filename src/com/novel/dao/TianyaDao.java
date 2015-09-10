package com.novel.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.novel.entity.Tianya;

@Repository
public class TianyaDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void updatePage(Tianya ty) throws Exception{
		String sql = "update tianya set pageNum=? WHERE id=?";
		Object[] values = new Object[]{ty.getPageNum(), ty.getId()};
		jdbcTemplate.update(sql, values);
	}
	
	public List<Tianya> queryAll(Map para) throws Exception{
		String sql = "SELECT * from tianya";
		Object[] values = new Object[]{};
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Tianya.class), values);
	}
	public int addTianya() throws Exception{
		String sql = "insert into tianya(id,bookid ) values (?,'1')";
		Object[] values = new Object[]{UUID.randomUUID().toString().replace("-", "")};
		return jdbcTemplate.update(sql, values);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
