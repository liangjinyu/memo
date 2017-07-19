

--查询一个框架的所有表
DECLARE @tableName VARCHAR(64) ,
    @schemaName VARCHAR(64) ,
    @exesql NVARCHAR(1024)

DECLARE cur CURSOR
FOR
    SELECT  t.[name] AS tablename ,
            s.[name] AS [schema]
    FROM    sys.tables AS t ,
            sys.schemas AS s
    WHERE   t.schema_id = s.schema_id
            AND s.[name] = 'ADM'

OPEN cur
FETCH NEXT FROM cur INTO @tableName, @schemaName
WHILE @@FETCH_STATUS = 0
    BEGIN
        PRINT @schemaName + '.' + @tableName
    
        FETCH NEXT FROM cur INTO @tableName, @schemaName
       
        SET @exesql = 'select top 3 * from ' + @schemaName + '.' + @tableName
        EXEC sp_executesql @exesql
    END
CLOSE cur
DEALLOCATE cur