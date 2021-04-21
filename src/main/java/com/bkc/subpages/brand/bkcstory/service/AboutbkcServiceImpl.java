package com.bkc.subpages.brand.bkcstory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.subpages.brand.bkcstory.dao.AboutbkcDAO;
import com.bkc.subpages.brand.bkcstory.vo.AboutbkcVO;

@Service
public class AboutbkcServiceImpl implements AboutbkcService{
	
	@Autowired
	private AboutbkcDAO dao;
	
	@Override
	public List<AboutbkcVO> AboutbkcList(AboutbkcVO aboutbkcVO){
		return dao.AboutbkcList(aboutbkcVO);
	}
}
