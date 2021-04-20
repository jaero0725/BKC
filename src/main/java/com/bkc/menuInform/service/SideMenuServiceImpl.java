package com.bkc.menuInform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.menuInform.dao.SideMenuDAO;
import com.bkc.menuInform.vo.SideMenuVO;

@Service
public class SideMenuServiceImpl implements SideMenuService {
	private SideMenuDAO sidemenuDao;

	@Autowired
	public void setSidemenuDao(SideMenuDAO sidemenuDao) {
		this.sidemenuDao = sidemenuDao;
	}

	@Override
	public SideMenuVO getSidemenu(String product_serial) {
		return sidemenuDao.getSidemenu(product_serial);
	}

	@Override
	public List<SideMenuVO> getSidemenuList() {
		return sidemenuDao.getSidemenuList();
	}

}
