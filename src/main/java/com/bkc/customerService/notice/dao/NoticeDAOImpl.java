package com.bkc.customerService.notice.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bkc.customerService.notice.vo.NoticeVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	private SqlSession sqlSession;

	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<NoticeVO> getNoticeList(NoticeVO notice) {
		System.out.println("getNoticeList() 수행");
		return sqlSession.selectList("getNoticeList", notice);
	}
	
	@Override
	public NoticeVO getNotice(NoticeVO notice) {
		System.out.println("getNotice() 수행");
		return sqlSession.selectOne("getNotice", notice);

	}

	@Override
	public int getNoticeTotalCnt(NoticeVO notice) {
		System.out.println("getNoticeTotalCnt() 수행");
		return sqlSession.selectOne("getNoticeTotalCnt");
	}
	

}
