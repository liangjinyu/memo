--1.É¾³ýÍâ¼üÔ¼Êø
use MERCHANTAUDIT
DECLARE c1 cursor for 
    select 'alter table [MERCHANTAUDIT].[mas].['+ object_name(parent_obj) + '] drop constraint ['+name+']; '
    from sysobjects 
    where xtype = 'F'
open c1
declare @c1 varchar(8000)
fetch next from c1 into @c1
while(@@fetch_status=0)
    begin 
        exec(@c1)
        fetch next from c1 into @c1
    end
close c1
deallocate c1 
--2.É¾³ý±í
use MERCHANTAUDIT
DECLARE c2 cursor for 
    select 'drop table [MERCHANTAUDIT].[mas].['+name +']; '
    from sysobjects 
    where xtype = 'u' 
open c2
declare @c2 varchar(8000)
fetch next from c2 into @c2
while(@@fetch_status=0)
    begin
        exec(@c2)
        fetch next from c2 into @c2
    end
close c2
deallocate c2