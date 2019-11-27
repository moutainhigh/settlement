package com.settlement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysDataDic;
import com.settlement.entity.SysDept;
import com.settlement.entity.SysPermission;
import com.settlement.entity.SysRole;
import com.settlement.mapper.SysDataDicMapper;
import com.settlement.mapper.SysDeptMapper;
import com.settlement.mapper.SysPermissionMapper;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.SysDeptService;
import com.settlement.service.SysRoleService;
import com.settlement.utils.Const;
import com.settlement.vo.SysPermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class SettlementApplicationTests {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysDataDicMapper sysDataDicMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Test
	void contextLoads() {
		List<SysPermission> lists = sysPermissionMapper.selectList(null);
		List<SysPermissionVo> sysPermissions = new ArrayList<SysPermissionVo>();
		if (lists != null && lists.size() > 0) {
			for (SysPermission sp : lists) {

				SysPermissionVo spv = new SysPermissionVo();
				spv.setId(sp.getId());
				spv.setPName(sp.getPName());
				spv.setUrl(sp.getUrl());
				spv.setPermission(sp.getPermission());
				spv.setParentId(sp.getParentId());
				spv.setIcon(sp.getIcon());

				sysPermissions.add(spv);


			}
		}


		List<SysPermissionVo> treeDataList = new ArrayList<>();
		//根据id添加到map当中
		Map<Integer,SysPermissionVo> map = new HashMap<>();
		for(SysPermissionVo sysPermission: sysPermissions){
			map.put(sysPermission.getId(),sysPermission);
			System.out.println(sysPermission.getPName()+",pid:"+sysPermission.getParentId());
		}
		//遍历查询生成树
       for(SysPermissionVo sysPermission: sysPermissions) {
           SysPermissionVo child = sysPermission;
           System.out.println("0-"+child.getPName()+",pid:"+child.getParentId());
           if(child.getParentId().equals(-1)) {
               treeDataList.add(child);
               System.out.println("1-child "+child.getPName()+",pid:"+child.getParentId());
           } else {
			   System.out.println("2-child "+child.getPName()+",pid:"+child.getParentId());
               SysPermissionVo parent=map.get(child.getParentId());
               //System.out.println("2-parent "+parent.getPName());
               parent.getChildrens().add(child);
           }

       }
	}

	@Test
	public void testTransactionManager() {
		QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
		queryWrapper.groupBy("pid");

		List<SysDataDic> sysDataDicList= sysDataDicMapper.selectList(queryWrapper);
		for(SysDataDic sysDataDic:sysDataDicList){
			System.out.println(sysDataDic);
		}

	}

	@Test
	public void testDataDicPageData() {
		QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
		//queryWrapper.like(StringUtils.isNotBlank(dataDicCo.getKeyword()),"dic_content",dataDicCo.getKeyword());

		QueryWrapper<SysDataDic> queryListWrapper = new QueryWrapper<>();
		queryListWrapper.eq("pid",Const.DATA_DIC_ROOT);
		queryListWrapper.orderByAsc("sort");
		List<SysDataDic> sysDataDicList = sysDataDicMapper.selectList(queryListWrapper);
		List<SysDataDic> sysPageDataDicList = new ArrayList<>();
		for(SysDataDic sysDataDic: sysDataDicList){
			sysPageDataDicList.add(sysDataDic);
			System.out.println("--------------1------------------");
			System.out.println(sysDataDic);

			QueryWrapper<SysDataDic> queryListWrapper2 = new QueryWrapper<>();
			queryListWrapper2.eq("pid",sysDataDic.getId());
			queryListWrapper2.orderByAsc("sort");
			List<SysDataDic> sysDataDicList2 = sysDataDicMapper.selectList(queryListWrapper2);
			for(SysDataDic sysDataDic2: sysDataDicList2){
				sysPageDataDicList.add(sysDataDic2);
				System.out.println("--------------2------------------");
				System.out.println(sysDataDic2);
			}
		}
		System.out.println("--------------end------------------");
		for(SysDataDic sysDataDic3: sysPageDataDicList){
			System.out.println(sysDataDic3);
		}
	}
	@Test
	public void getPermissons(){
		QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id",Const.PERMISSION_ROOT_ID);
		List<SysPermission> sysPermissions=sysPermissionMapper.selectList(queryWrapper);
		Map<SysPermission,Map<SysPermission,List<SysPermission>>> map = new HashMap<>();
		Map<SysPermission,List<Map<SysPermission,List<SysPermission>>>> maproot = new HashMap<>();
		for(SysPermission sysPermission:sysPermissions) {

			QueryWrapper<SysPermission> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("parent_id",sysPermission.getId());
			List<SysPermission> childSysPermissions=sysPermissionMapper.selectList(queryWrapper2);
			List<Map<SysPermission,List<SysPermission>>> list  = new ArrayList<>();

			for(SysPermission child: childSysPermissions) {
				Map<SysPermission,List<SysPermission>> map2 = new HashMap<>();

				QueryWrapper<SysPermission> queryWrapper3 = new QueryWrapper<>();
				queryWrapper3.eq("parent_id",child.getId());
				List<SysPermission> childSysPermissions2=sysPermissionMapper.selectList(queryWrapper3);
				map2.put(child,childSysPermissions2);
				list.add(map2);
			}
			maproot.put(sysPermission,list);
		}

		//--------sout---------
		for(Map.Entry<SysPermission,List<Map<SysPermission,List<SysPermission>>>> mroot:maproot.entrySet()) {
			System.out.println("id:"+ mroot.getKey().getId() +", value:"+mroot.getKey().getPName()+",size:"+mroot.getValue().size());
			for(Map<SysPermission,List<SysPermission>> listchid: mroot.getValue()) {
				for(Map.Entry<SysPermission,List<SysPermission>> m: listchid.entrySet()) {
						System.out.println("\t--id:"+m.getKey().getId()+", value:"+m.getKey().getPName());
						for(SysPermission sysPermission: m.getValue()) {
							System.out.println("\t\t----id:"+sysPermission.getId()+", value:"+sysPermission.getPName());
						}

				}
			}

		}

	}

	@Test
	public void testInsertReturnId() {
		SysRole sysRole = new SysRole();
		sysRole.setRoleCnName("tes");
		sysRole.setRoleEnName("tes2");
		sysRole.setRoleCode("tes3");
		sysRole.setRemark("rrr");
		sysRole.setCreateTime(new Date());
		sysRole.setDelFlag(Const.DEL_FLAG_N);
		Integer ret = sysRoleMapper.insert(sysRole);
		System.out.println(sysRole.getId());
	}

	@Test
	public void test09(){
		QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id",0);
		//取得第一级的权限菜单
		List<SysPermissionVo> sysPermissions=sysPermissionMapper.getSysPermissionVo(0);
		System.out.println(sysPermissions);
	}

	@Test
	public void testMapKey(){
		Map<SysPermissionVo,List<SysPermissionVo>> map = new HashMap<>();
		List<SysPermissionVo> sysPermissions=getPermissionVoByParentId(Const.PERMISSION_ROOT_ID);
		System.out.println(sysPermissions.get(0).hashCode());
		System.out.println(sysPermissions.get(1).hashCode());
		System.out.println(sysPermissions.get(0).equals(sysPermissions.get(1)));
		for(SysPermissionVo sysPermissionVo:sysPermissions){
			List<SysPermissionVo> sysPermissions2=getPermissionVoByParentId(sysPermissionVo.getId());
			map.put(sysPermissionVo,sysPermissions2);
		}
		System.out.println(map);
	}
	@Test
	public void testMapKey2(){
		Map<SysPermission,List<SysPermission>> map = new HashMap<>();
		QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id",Const.PERMISSION_ROOT_ID);
		List<SysPermission> sysPermissions=sysPermissionMapper.selectList(queryWrapper);
		System.out.println(sysPermissions.get(0).hashCode());
		System.out.println(sysPermissions.get(1).hashCode());
		System.out.println(sysPermissions.get(0).equals(sysPermissions.get(1)));
		for(SysPermission sysPermissionVo:sysPermissions){
			QueryWrapper<SysPermission> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("parent_id",sysPermissionVo.getId());
			List<SysPermission> sysPermissions2=sysPermissionMapper.selectList(queryWrapper2);
			map.put(sysPermissionVo,sysPermissions2);
		}
		System.out.println(map);
	}

	public List<SysPermissionVo> getPermissionVoByParentId(Integer parent_id){
		QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id",parent_id);
		List<SysPermission> sysPermissions = sysPermissionMapper.selectList(queryWrapper);
		List<SysPermissionVo> sysPermissionVos = new ArrayList<>();
		for(SysPermission sysPermission : sysPermissions) {
			SysPermissionVo sysPermissionVo = new SysPermissionVo();
			sysPermissionVo.setId(sysPermission.getId());
			sysPermissionVo.setParentId(sysPermission.getParentId());
			sysPermissionVo.setPCode(sysPermission.getPCode());
			sysPermissionVo.setPName(sysPermission.getPName());
			sysPermissionVo.setTitle(sysPermission.getPName());
			sysPermissionVo.setChecked(false);
			sysPermissionVos.add(sysPermissionVo);
		}
		return sysPermissionVos;

	}
}
