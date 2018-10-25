sample
===
* 注释

	select #use("cols")# from ui_component  where  #use("condition")#

cols
===
	id,parent_id,name,title,element_id,sort,is_use,atime,utime,crm_power_id,ui_router_id

updateSample
===
	
	id=#id#,parent_id=#parentId#,name=#name#,title=#title#,element_id=#elementId#,sort=#sort#,is_use=#isUse#,atime=#atime#,utime=#utime#,power_id=#powerId#

condition
===

	1 = 1  
	@if(!isEmpty(id)){
	 and id=#id#
	@}
	@if(!isEmpty(parentId)){
	 and parent_id=#parentId#
	@}
	@if(!isEmpty(name)){
	 and name=#name#
	@}
	@if(!isEmpty(title)){
	 and title=#title#
	@}
	@if(!isEmpty(elementId)){
	 and element_id=#elementId#
	@}
	@if(!isEmpty(sort)){
	 and sort=#sort#
	@}
	@if(!isEmpty(isUse)){
	 and is_use=#isUse#
	@}
	@if(!isEmpty(atime)){
	 and atime=#atime#
	@}
	@if(!isEmpty(utime)){
	 and utime=#utime#
	@}
	@if(!isEmpty(powerId)){
	 and power_id=#powerId#
	@}
	
	
tableName
===
ui_component

getChildListById
===
	select #use("cols")# from #use("tableName")# where 
	FIND_IN_SET(id,getChildList(#componentId#)) 
	and id !=#componentId#
	order by sort
	
