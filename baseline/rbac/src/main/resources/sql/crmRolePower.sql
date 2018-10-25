cols
===
	id,
		 idPermission,
		 idRole,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
role_power

batchdelete
===

update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)