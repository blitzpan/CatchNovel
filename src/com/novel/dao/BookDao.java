package com.novel.dao;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.novel.entity.Book;
@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public Map addBook(Book book) throws Exception{
		String sql = "INSERT into book(id,bookid,pagenum,`content`,gatherdate) values(?,?,?,?,SYSDATE())";
		Object[] values = new Object[]{UUID.randomUUID().toString().replaceAll("-", ""),
				book.getBookId(), book.getPageNum(), book.getContent()};
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