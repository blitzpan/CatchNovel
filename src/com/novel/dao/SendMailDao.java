package com.novel.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.novel.entity.SendTask;

public class SendMailDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * @Description:获取所有需要发送邮件的章节和用户信息 
	 * @param @return
	 * @param @throws Exception   
	 * @return List<SendTask>  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月12日
	 */
	public List<SendTask> queryAllNeedSend() throws Exception{
		String sql = "select c.id chapterid,u.email from chapter c,user_book ub,user u"
				+ " where c.sendMail=1 and ub.sendMail=1"
				+ " and c.bookInfoId = ub.bookId"
				+ " and ub.userId=u.id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SendTask.class));
	}
	/**
	 * @Description: 
	 * @param @param stl
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月12日
	 */
	public void batchAddTask(final List<SendTask> stl) throws Exception{
		//添加任务
		String sql = "insert into sendmailtask(id,chapterid,status,email) values(replace(uuid(),'-',''),?,0,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SendTask st = stl.get(i);
				ps.setString(1, st.getChapterId());
				ps.setString(2, st.getEmail());
			}
			@Override
			public int getBatchSize() {
				return stl.size();
			}
		});
		//修改章节信息为已经发送
		sql = "update chapter set sendmail=1 where id=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SendTask st = stl.get(i);
				ps.setString(1, st.getChapterId());
			}
			@Override
			public int getBatchSize() {
				return stl.size();
			}
		});
	}
	/**
	 * @Description:获取一个任务 
	 * @param @return
	 * @param @throws Exception   
	 * @return SendTask  
	 * @throws
	 * @author Panyk
	 * @date 2016年1月12日
	 */
	public SendTask getOneTask() throws Exception{
		SendTask st = null;
		String sql = "select * from sendMailTask limit 0,1";
		try{
			st = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(SendTask.class));
		}catch(Exception e){
		}
		return st;
	}
	
	public void setSended(SendTask st) throws Exception{
		String sql = "delete from sendMailTask where id=?";
		jdbcTemplate.update(sql, st.getId());
	}
	
	
	
}
