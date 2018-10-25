cols
===
	id,
		 parentId,
		 name,
		 sort,
		 href,
		 target,
		 icon,
		 isShow,
		 permissionId,
		 createUserId,
		 remarks,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
menu

batchdelete
===
update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)