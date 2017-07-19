



CREATE TABLE ma_label(
    [labelid] [int] PRIMARY KEY IDENTITY(1,1),     --标签id
    [labelname] [varchar](64) 					   --标签名字
)
GO


CREATE TABLE ma_product(
    [productid] [int] PRIMARY KEY IDENTITY(1,1),     
       [productname] [varchar](64) 	,     
    [labelname] [varchar](64) 					   
)
GO

INSERT INTO ma_label VALUES('培训')
INSERT INTO ma_label VALUES('租房')
INSERT INTO ma_label VALUES('汽车')
INSERT INTO ma_label VALUES('家装')
INSERT INTO ma_label VALUES('消费贷')
INSERT INTO ma_label VALUES('银商')

INSERT INTO ma_label VALUES('圣诞活动')
INSERT INTO ma_label VALUES('元旦活动')
INSERT INTO ma_label VALUES('双十一活动')


INSERT INTO ma_product VALUES('java','1,9')
INSERT INTO ma_product VALUES('iphone7','5,6,7')
INSERT INTO ma_product VALUES('test','')





  Create FUNCTION [mas].[SplitToTable]
  (
      @SplitString nvarchar(max),
      @Separator nvarchar(10)=' '
  )
  RETURNS @SplitStringsTable TABLE
  (
  [id] int identity(1,1),
  [subvalue] nvarchar(max)
 )
 AS
 BEGIN
     DECLARE @CurrentIndex int;
     DECLARE @NextIndex int;
     DECLARE @ReturnText nvarchar(max);
     SELECT @CurrentIndex=1;
     WHILE(@CurrentIndex<=len(@SplitString))
         BEGIN
             SELECT @NextIndex=charindex(@Separator,@SplitString,@CurrentIndex);
             IF(@NextIndex=0 OR @NextIndex IS NULL)
                 SELECT @NextIndex=len(@SplitString)+1;
                 SELECT @ReturnText=substring(@SplitString,@CurrentIndex,@NextIndex-@CurrentIndex);
                 INSERT INTO @SplitStringsTable([subvalue]) VALUES(@ReturnText);
                 SELECT @CurrentIndex=@NextIndex+1;
             END
     RETURN;
 END


SELECT * FROM SplitToTable('1,22,3,444,55',',')



SELECT * FROM ma_label
SELECT * FROM ma_product

SELECT * FROM ma_product p CROSS APPLY SplitToTable(p.labelname,',')

SELECT p.productid,productname,l.labelname FROM (
SELECT * FROM ma_product t OUTER APPLY SplitToTable(t.labelname,',')
) p LEFT JOIN ma_label l ON p.value = l.labelid



-----sql server for xml path： select * from table t for XML PATH
SELECT * FROM memedaidb.mkt.PROGRAM




SELECT *  FROM 
( 
 SELECT *,V_XML=CONVERT(XML, '<sn>' + REPLACE(t.image_ids,',','</sn><sn>') + '</sn>') FROM mas.ma_merchant_image t
 ) A
  CROSS APPLY
   (   
    SELECT image_id=N.v.value('.','varchar(10)')
    FROM A.V_XML.nodes('/sn') N (v)
   ) B
   
   
   SELECT * FROM mas.ma_role
   SELECT * FROM mas.ma_role FOR XML PATH
   
   
   CREATE TABLE ma_label(
    [labelid] [int] PRIMARY KEY IDENTITY(1,1),     --标签id
    [labelname] [varchar](64) 					   --标签名字
)
GO

SELECT labelname+',' FROM (SELECT 'a' name,* FROM ma_label) t 
  WHERE sName=A.sName 
  FOR XML PATH('')
  
  
  
SELECT 'a' name,* FROM ma_label


----------------------------------------------
https://msdn.microsoft.com/zh-cn/library/ms188282.aspx

IF OBJECT_ID('dbo.person', 'U') IS NOT NULL
    DROP TABLE dbo.person
GO

CREATE TABLE dbo.person(
	name [varchar](128) NULL,
	hobby [varchar](128) NULL
) ON [PRIMARY]
GO

INSERT INTO dbo.person VALUES('jack','running');
INSERT INTO dbo.person VALUES('jack','swiming');
INSERT INTO dbo.person VALUES('rose','running');
INSERT INTO dbo.person VALUES('rose','cooking');

INSERT INTO dbo.person VALUES('lala','running,swiming,cooking');

SELECT * FROM dbo.person
SELECT * FROM dbo.person for XML PATH

--拆分
SELECT *  FROM 
( 
 SELECT *,V_XML=CONVERT(XML, '<sn>' + REPLACE(t.hobby,',','</sn><sn>') + '</sn>') FROM dbo.person t
 ) A
  CROSS APPLY
   (   
    SELECT hobby=N.v.value('.','varchar(10)')
    FROM A.V_XML.nodes('/sn') N (v)
   ) B


--合并
  
SELECT name,
(SELECT hobby+',' FROM  dbo.person 
  WHERE name=t.name 
  FOR XML PATH('')) AS list
FROM dbo.person t GROUP BY name
