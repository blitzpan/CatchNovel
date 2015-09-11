package com.novel.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	/**
	 * 查询所有满足条件的章节
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public List queryBooks(Book book) throws Exception{
		String sql = "select * from book book where 1=1 ";
		List<Object> values = new ArrayList();
		this.makeQueryBooksSql(sql, book, values);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Book.class));
	}
	
	private String makeQueryBooksSql(String sql, Book book, List values) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" and book.sendmail=" + book.getSendMail());
		sql += sb.toString();
		return sql;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}