package com.biz.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.biz.ISchoolInfoBiz;
import com.po.SchoolInfo;
import com.service.dao.DaoService;

@Service("SchoolInfoBizImpl")
public class SchoolInfoBizImpl implements ISchoolInfoBiz {

	@Resource(name="DaoService")
	private DaoService daos;
	
	@Override
	public List<SchoolInfo> findColleges() {
		return daos.getSchoolInfoDAO().findByLevel(0);
	}

	@Override
	public List<SchoolInfo> findByCollegeId(String pid) {
		return daos.getSchoolInfoDAO().findByPCode(pid);
	}

}
