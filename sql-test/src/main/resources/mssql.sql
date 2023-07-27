use mssql_eq
create table MyTopic
(
    Id              nvarchar(128)  not null
        constraint PK_MyTopic
        primary key,
    Stars      int,
    CreateTime      datetime       ,
    Title      nvarchar(128)
)
    go

