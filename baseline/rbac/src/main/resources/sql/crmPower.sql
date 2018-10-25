cols
===
	id,
		 name,
		 stp_method stpMethod,
		 stp_url stpUrl,
		 menuId,
		 createUserId,
		 remarks,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
power

batchdelete
===
update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)

getPowerByUrl
===
select #use("cols")# from #use("tableName")# where stp_method=#stpMethod# and #stpUrl# like stp_url