


exec sp_addlinkedserver  'mmddbuat','','SQLOLEDB','99.48.66.12,1433'

exec sp_addlinkedsrvlogin 'mmddbuat','false',null,'migrate_dev','migrate_dev'

go

SELECT DISTINCT apply_source FROM MERCHANTAUDIT.mas.ma_merchandise_applyinfo


