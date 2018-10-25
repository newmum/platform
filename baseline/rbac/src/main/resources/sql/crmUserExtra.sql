condtion
===
where 1=1 and id = #id#

tableName
===
user_extra

getByUserId
===
select * from #use("tableName")# where user_id=#userId#

batchdelete
===
update #use("tableName")# set status=#status# where id in(
@for(id in ids){
    #id#  #text(idLP.last?"":"," )#
@}
)