cols
===
	id,userId,roleId,utime,atime,status
	
condtion
===
where 1=1 and id = #id#

tableName
===
user_role

deleteByUserId
===
delete FROM #use("tableName")# WHERE userId = #userId#