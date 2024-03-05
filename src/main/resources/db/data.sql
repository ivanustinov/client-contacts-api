insert into clients values ('42abcd2b-8b9c-4af9-88f7-0bc180cf74b4', 'Сидоров Иван Петрович');
insert into clients values ('42abcd2b-8b9c-4af9-88f7-0bc180cf74b5', 'Петров Иван Петрович');

insert into contacts(id, client_id, type, value)
values ('12abcd2b-8b9c-4af9-88f7-0bc180cf74b6', '42abcd2b-8b9c-4af9-88f7-0bc180cf74b4', 'PHONE', '+79120361878');

insert into contacts(id, client_id, type, value)
values ('12abcd2b-8b9c-4af9-88f7-0bc180cf74b7', '42abcd2b-8b9c-4af9-88f7-0bc180cf74b4', 'EMAIL', 'ivanustinov1985@yandex.ru');