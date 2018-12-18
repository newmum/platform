cols
===
	TID,ACCOUNT

condtion
===
where 1=1 and TID = #id#

tableName
===
RM_USER_T

get
===
select * from #use("tableName")# #use("condtion")#

delete
===
update #use("tableName")# set status=#sysUser.status# where TID=#sysUser.id#

batchdelete
===
update #use("tableName")# set IS_DEL=#IS_DEL# where TID in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)

getRoleList
===
select r.TID,r.ROLE_NAME from #use("tableName")# u
left join RM_ROLE_PRIV_RELA_T ur ON u.TID=ur.ROLE_ID
left join RM_ROLE_T r ON r.TID=ur.ROLE_ID
where u.TID=#userId# AND r.TID IS NOT NULL
group by r.TID,r.ROLE_NAME

getPRIVList
===
select * from RM_PRIV_T where TID in
	(select PRIV_ID From RM_ROLE_PRIV_RELA_T where
		ROLE_ID
		in(
		select ROLE_ID From RM_USER_ROLE_RELA_T where USER_ID=#userId#
		)
	)

getMenuList
===
select r.*,p.METHOD method,p.URL url from RM_MENU_T r
left join RM_PRIV_T p on  r.PRIV_ID =p.TID
where FIND_IN_SET(r.TID,getRouterParentList(
(
select group_concat(r.TID) from RM_MENU_T r
left join RM_PRIV_T p on  r.PRIV_ID =p.TID
	where r.PRIV_ID
		in(
		select TID from RM_PRIV_T where TID in
			(select PRIV_ID from RM_ROLE_PRIV_RELA_T where ROLE_ID
			in(select ROLE_ID from RM_USER_ROLE_RELA_T where USER_ID=#userId#)
			)
		)
)
)) order by r.SORT desc


queryUsers
===

select * from #use("tableName")# WHERE TID=#userId#
	@ orm.many({"TID":"userId"},"user.getRoleList","Role");
	@ orm.many({"TID":"userId"},"user.getMenuList","Menu");
	@ orm.many({"TID":"userId"},"user.getPRIVList","PRIV");


