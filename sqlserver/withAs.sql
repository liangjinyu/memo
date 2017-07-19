
if exists (select * from sysobjects where id = OBJECT_ID('[InStorages]') and OBJECTPROPERTY(id, 'IsUserTable') = 1) 
DROP TABLE [InStorages]

CREATE TABLE [InStorages] (
[id] [int]  NULL,
[PorductsCode] [varchar]  (50) NULL,
[InStoragesNumber] [int]  NULL,
[InStoragesTime] [datetime]  NULL)

INSERT [InStorages] ([id],[PorductsCode],[InStoragesNumber],[InStoragesTime]) VALUES ( 1,'111',100,'2011-1-1 0:00:00')
INSERT [InStorages] ([id],[PorductsCode],[InStoragesNumber],[InStoragesTime]) VALUES ( 2,'111',20,'2011-1-2 0:00:00')
INSERT [InStorages] ([id],[PorductsCode],[InStoragesNumber],[InStoragesTime]) VALUES ( 3,'222',45,'2011-1-1 0:00:00')
INSERT [InStorages] ([id],[PorductsCode],[InStoragesNumber],[InStoragesTime]) VALUES ( 4,'222',55,'2011-1-3 0:00:00')

 

 

 

 

if exists (select * from sysobjects where id = OBJECT_ID('[TheStorages]') and OBJECTPROPERTY(id, 'IsUserTable') = 1) 
DROP TABLE [TheStorages]

CREATE TABLE [TheStorages] (
[id] [int]  NULL,
[PorductsCode] [varchar]  (50) NULL,
[TheStoragesNumber] [int]  NULL,
[TheStoragesTime] [datetime]  NULL)

INSERT [TheStorages] ([id],[PorductsCode],[TheStoragesNumber],[TheStoragesTime]) VALUES ( 1,'111',60,'2011-1-2 0:00:00')
INSERT [TheStorages] ([id],[PorductsCode],[TheStoragesNumber],[TheStoragesTime]) VALUES ( 2,'111',220,'2011-1-3 0:00:00')
INSERT [TheStorages] ([id],[PorductsCode],[TheStoragesNumber],[TheStoragesTime]) VALUES ( 3,'333',85,'2011-1-1 0:00:00')
INSERT [TheStorages] ([id],[PorductsCode],[TheStoragesNumber],[TheStoragesTime]) VALUES ( 4,'222',15,'2011-1-3 0:00:00')

 
 WITH   TT
          AS ( SELECT   PorductsCode ,
                        InStoragesTime AS time
               FROM     InStorages
               UNION
               SELECT   PorductsCode ,
                        TheStoragesTime AS time
               FROM     TheStorages
             )
    SELECT  ROW_NUMBER() OVER ( ORDER BY TT.PorductsCode ) AS id ,
            TT.PorductsCode ,
            ISNULL(b.InStoragesNumber, 0) AS '入库数量' ,
            ISNULL(c.TheStoragesNumber, 0) AS '出库数量' ,
            CONVERT(VARCHAR(10), TT.time, 120) AS '时间'
    FROM    TT
            LEFT JOIN InStorages b ON b.InStoragesTime = TT.time
                                      AND b.PorductsCode = TT.PorductsCode
            LEFT JOIN TheStorages c ON c.TheStoragesTime = TT.time
                                       AND c.PorductsCode = TT.PorductsCode