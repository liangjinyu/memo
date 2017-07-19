


IF OBJECT_ID('mas.ma_policy_bracket_conf', 'U') IS NOT NULL
    DROP TABLE mas.ma_policy_bracket_conf
GO
CREATE TABLE [mas].[ma_policy_bracket_conf](
	[id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
	[policy_bracket] [varchar](128) NOT NULL
)
GO

SELECT * INTO #temp1 FROM mas.ma_policy_bracket_conf
SELECT * INTO #temp2 FROM mas.ma_policy_bracket_conf

SELECT * FROM  #temp1 
SELECT * FROM #temp2 

UPDATE #temp1 SET policy_bracket = 'AA' WHERE id = 10;
DELETE FROM #temp2 WHERE id = 20


MERGE #temp2 AS dst
    USING #temp1 AS src
    ON dst.id = src.id
    WHEN MATCHED
        THEN 
UPDATE    SET
        dst.policy_bracket = src.policy_bracket
    WHEN NOT MATCHED
        THEN 
 INSERT ( policy_bracket )
          VALUES
        ( src.policy_bracket
        );

DROP TABLE #temp1