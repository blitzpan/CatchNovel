package com.novel.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.novel.entity.BookInfo;
@Repository
public class BookInfoDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * @Description:��ѯ���е��鼮��Ϣ 
	 * @param @return
	 * @param @throws Exception   
	 * @return List  
	 * @throws
	 * @author Panyk
	 * @date 2015��9��29��
	 */
	public List queryAllBookInfos() throws Exception{
		String sql = "SELECT * from bookinfo";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(BookInfo.class) );
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}