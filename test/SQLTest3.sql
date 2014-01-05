SELECT *
FROM Test1,Test2,Test3
WHERE Test1.A = Test2.B
And Test2.C = Test3.D
And Test3.Field = "asdf"
AND Test1.Field3 = "qwer";