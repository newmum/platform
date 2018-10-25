cols
===
	id,
		 name,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
group_info

batchdelete
===

update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)