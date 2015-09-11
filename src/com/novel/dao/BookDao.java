package com.novel.dao;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public Map addBook(String id) throws Exception{
		if(id.equals("5")){
			id = "4";
		}
		String sql = "INSERT into book(id,bookid,pagenum,`content`,gatherdate) values(?,'1','1','a',SYSDATE())";
		Object[] values = new Object[]{id };
		int i = jdbcTemplate.update(sql,values);
		System.out.println("更新条数=" + i);
		return null;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}