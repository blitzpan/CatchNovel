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

import com.novel.entity.Chapter;
import com.novel.entity.ChapterCache;
@Repository
public class ChapterDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * @Description:获取一条数据，不存在则返回null 
	 * @param @param id
	 * @param @return
	 * @param @throws Exception   
	 * @return Chapter  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月12日
	 */
	public Chapter getOneChapterById(String id) throws Exception{
		String sql = "select c.*,bi.authorname,bi.articleName,bi.homeUrl from chapter c,bookinfo bi"
				+ " where c.id=?"
				+ " and c.bookInfoId=bi.bookId";
		Chapter c = null;
		try{
			c = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(Chapter.class));
			ChapterCache.put(c.getId(), c);
		}catch(Exception e){
		}
		return c;
	}
	public Map addChapter(Chapter chapter) throws Exception{
		//根据书号和章节查询content长度，如果长度不一致那么update，update结果为0那么insert
		int dbLen = 0;
		String sql = "SELECT CHAR_LENGTH(content) from Chapter WHERE bookinfoId=? and pageNum=?";
		try{
			dbLen = jdbcTemplate.queryForInt(sql, new Object[]{chapter.getBookInfoId(), chapter.getPageNum()});
		}catch(Exception e){
		}
		if(chapter.getContent().length()>dbLen){
			sql = "UPDATE chapter set content=?,sendMail=1 WHERE bookinfoId=? and pageNum=?";
			int i = jdbcTemplate.update(sql, new Object[]{chapter.getContent(), chapter.getBookInfoId(), chapter.getPageNum()});
			if(i<1){
				sql = "INSERT into chapter(id,bookinfoid,pagenum,`content`,url,gatherdate) values(?,?,?,?,?,SYSDATE())";
				Object[] values = new Object[]{UUID.randomUUID().toString().replaceAll("-", ""),
						chapter.getBookInfoId(), chapter.getPageNum(), chapter.getContent(),chapter.getUrl()};
				i = jdbcTemplate.update(sql,values);
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询所有满足条件的章节
	 * @param chapter
	 * @return
	 * @throws Exception
	 */
	public List queryChapters(Chapter chapter) throws Exception{
		String sql = "SELECT b.id,b.bookinfoid,b.url,b.pageNum,b.content,DATE_FORMAT(b.gatherDate,'%Y-%c-%e %T') gatherDate,b.sendMail from chapter b where 1=1 ";
		List<Object> values = new ArrayList();
		sql += this.makeQueryChaptersSql(chapter, values);//这里的sql必须sql="返回值"，在子方法中的改变str不会改变父方法中的Str。
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Chapter.class), values.toArray());
	}
	
	private String makeQueryChaptersSql(Chapter chapter, List values) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" and b.sendmail=" + chapter.getSendMail());
		return sb.toString();
	}
	public void test() throws Exception{
		String sql = "delete from book";
		jdbcTemplate.update(sql);
		String oldSql = "insert into book(id,bookid,content,gatherdate) values('idd','1','1',SYSDATE())";
		for (int i = 0; i < 1; i++) {
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
		
		//新增一个错误的插入，判断这里肯定会回滚，从而判断是否在同一个事务之中。
		if(true){
//			throw new RuntimeException("test");
			String sql = "insert into chapter(id) values('1')";
			System.out.println("jdbc = " + jdbcTemplate);
			jdbcTemplate.update(sql);
		}
//		return max;
		return 0;
	}
	/**
	 * @Description:更新章节为已发送完成 
	 * @param @param chapter
	 * @param @return
	 * @param @throws Exception   
	 * @return int  
	 * @throws
	 * @author Panyk
	 * @date 2015年9月28日
	 */
	public int updateSendState(Chapter chapter) throws Exception{
		String sql = "UPDATE chapter set sendMail=0 WHERE id=?";
		return jdbcTemplate.update(sql, chapter.getId());
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}