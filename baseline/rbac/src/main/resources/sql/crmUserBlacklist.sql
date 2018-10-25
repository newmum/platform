cols
===
	id,
		 userId,
		 ip,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
user_blacklist

batchdelete
===

update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)