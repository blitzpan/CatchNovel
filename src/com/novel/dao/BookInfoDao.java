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
	 * @Description:查询所有的书籍信息 
	 * @param @return
	 * @param @throws Exception   
	 * @return List  
	 * @throws
	 * @author Panyk
	 * @date 2015年9月29日
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