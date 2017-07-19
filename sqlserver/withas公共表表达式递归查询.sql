


SELECT * FROM CRM.ADDRESS


WITH cte_a AS(
SELECT * ,0 AS newLevel FROM CRM.ADDRESS WHERE parent_id IS NULL
UNION ALL 
SELECT b.*,cte_a.newLevel+1 AS newLevel FROM CRM.ADDRESS b
INNER JOIN cte_a ON cte_a.id = b.PARENT_ID
)

SELECT * FROM cte_a



WITH cte_a AS(
SELECT *  FROM CRM.ADDRESS WHERE name = 'ÑÎ¶¼Çø'
UNION ALL 
SELECT b.*   FROM CRM.ADDRESS b
INNER JOIN cte_a ON cte_a.PARENT_ID= b.id 
--cte_a.id = b.PARENT_ID
)

SELECT * FROM cte_a


