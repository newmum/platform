cols
===
	id,name

condtion
===
where 1=1 and id = #id#

tableName
===
crm_user

get
===
select * from #use("tableName")# #use("condtion")#

delete
===
update #use("tableName")# set status=#sysUser.status# where id=#sysUser.id#

batchdelete
===
update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)

getRoleList
===
select r.id,r.name from #use("tableName")# u
left join crm_user_role ur ON u.id=ur.crm_user_id
left join crm_role r ON r.id=ur.crm_role_id
where u.id=#userId# AND r.id IS NOT NULL
group by r.id

getPowerList
===
select * from crm_power where id in
	(select crm_power_id From crm_role_power where
		crm_role_id
		in(
		select crm_role_id From crm_user_role where crm_user_id=#userId#
		)
	)

getMenuList
===
select r.*,p.method method,p.url url from ui_router  r
left join crm_power p on  r.crm_power_id =p.id
where FIND_IN_SET(r.id,getRouterParentList(
(
select group_concat(r.id) from ui_router  r
left join crm_power p on  r.crm_power_id =p.id
	where r.crm_power_id
		in(
		select id from crm_power where id in
			(select crm_power_id from crm_role_power where crm_role_id
			in(select crm_role_id from crm_user_role where crm_user_id=#userId#)
			)
		)
)
)) order by r.sort desc


queryUsers
===

select * from #use("tableName")# WHERE id=#userId#
	@ orm.many({"id":"userId"},"user.getRoleList","Role");
	@ orm.many({"id":"userId"},"user.getMenuList","Menu");
	@ orm.many({"id":"userId"},"user.getPowerList","Power");


