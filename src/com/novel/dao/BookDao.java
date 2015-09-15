package com.novel.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
		System.out.println("��������=" + i);
		return null;
	}
	/**
	 * ��ѯ���������������½�
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public List queryBooks(Book book) throws Exception{
		String sql = "select * from book book where 1=1 ";
		List<Object> values = new ArrayList();
		sql += this.makeQueryBooksSql(book, values);//�����sql����sql="����ֵ"�����ӷ����еĸı�str����ı丸�����е�Str��
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Book.class), values.toArray());
	}
	
	private String makeQueryBooksSql(Book book, List values) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" and book.sendmail=" + book.getSendMail());
		return sb.toString();
	}
	public void test() throws Exception{
		String sql = "delete from book";
		jdbcTemplate.update(sql);
		String oldSql = "insert into book(id,bookid,content,gatherdate) values('idd','1','1',SYSDATE())";
		for (int i = 0; i < 10000; i++) {
			sql = oldSql.replace("idd", i+"");
			jdbcTemplate.update(sql);
		}
	}
	
	public void batchTest() throws Exception{
		System.out.println("jdbc = " + jdbcTemplate);
		String sql = "delete from book";
		jdbcTemplate.update(sql);
		String oldSql = "insert into book(id,bookid,content,gatherdate) values(?,'1','1',SYSDATE())";
		jdbcTemplate.batchUpdate(oldSql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i) throws SQLException {
				pst.setString(1, i + "");
			}
			public int getBatchSize() {
				
				return 10000;
			}
		});
	}
	public int queryMax() throws Exception{
//		String sql = "SELECT  max(cast(id as UNSIGNED int)) from book;";
//		int max = jdbcTemplate.queryForInt(sql);
		
		//����һ������Ĳ��룬�ж�����϶���ع����Ӷ��ж��Ƿ���ͬһ������֮�С�
		if(true){
//			throw new RuntimeException("test");
			String sql = "insert into book(id) values('1')";
			System.out.println("jdbc = " + jdbcTemplate);
			jdbcTemplate.update(sql);
			
		}
//		return max;
		return 0;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}