getList
===
SELECT column_name jdbc_field, data_type jdbc_type,column_comment comments,
column_key columnKey,character_maximum_length length,extra FROM information_schema.columns
WHERE table_name =#tableName# and table_schema = (select database()) order by ordinal_position