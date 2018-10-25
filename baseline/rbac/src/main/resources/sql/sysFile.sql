sample
===
* 注释

	select #use("cols")# from sys_file  where  #use("condition")#

cols
===
	id,name,true_name,sort,type,parent_id,path,user_id,status,create_user_id,atime,utime,remarks

updateSample
===
	
	id=#id#,name=#name#,true_name=#trueName#,sort=#sort#,type=#type#,parent_id=#parentId#,path=#path#,user_id=#userId#,status=#status#,create_user_id=#createUserId#,atime=#atime#,utime=#utime#,remarks=#remarks#


tableName
===
sys_file

condition
===

	1 = 1  
	@if(!isEmpty(id)){
	 and id=#id#
	@}
	@if(!isEmpty(name)){
	 and name=#name#
	@}
	@if(!isEmpty(trueName)){
	 and true_name=#trueName#
	@}
	@if(!isEmpty(sort)){
	 and sort=#sort#
	@}
	@if(!isEmpty(type)){
	 and type=#type#
	@}
	@if(!isEmpty(parentId)){
	 and parent_id=#parentId#
	@}
	@if(!isEmpty(path)){
	 and path=#path#
	@}
	@if(!isEmpty(userId)){
	 and user_id=#userId#
	@}
	@if(!isEmpty(status)){
	 and status=#status#
	@}
	@if(!isEmpty(createUserId)){
	 and create_user_id=#createUserId#
	@}
	@if(!isEmpty(atime)){
	 and atime=#atime#
	@}
	@if(!isEmpty(utime)){
	 and utime=#utime#
	@}
	@if(!isEmpty(remarks)){
	 and remarks=#remarks#
	@}
	
getFileParentList
===
select #use("cols")# from #use("tableName")# where FIND_IN_SET(id,getFileParentList(#fileId#))

getFileChildList
===
select #use("cols")# from #use("tableName")# where FIND_IN_SET(id,getFileChildList(#fileId#))