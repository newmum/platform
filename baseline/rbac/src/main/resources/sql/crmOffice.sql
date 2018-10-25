cols
===
	id,
		 parentId,
		 name,
		 sort,
		 code,
		 type,
		 grade,
		 address,
		 zipCode,
		 master,
		 phone,
		 fax,
		 email,
		 createUserId,
		 remarks,
		utime,atime,status
	
	
condtion
===
where 1=1 and id = #id#

tableName
===
office

batchdelete
===
update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)