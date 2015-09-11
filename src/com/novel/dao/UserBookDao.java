package com.novel.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.novel.entity.Book;
import com.novel.entity.UserBook;
@Repository
public class UserBookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<UserBook> queryUserBook(Book book) throws Exception{
		String sql = " SELECT u.id userid,u.email from user_book ub,user u "
				+ " where ub.userid=u.id "
				+ " and ub.sendMail=1 "
				+ " and ub.bookid=?";
		Object[] values = new Object[]{book.getBookId()};
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserBook.class), values);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}