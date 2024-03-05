-- Создание таблицы для хранения информации о клиентах
create table if not exists clients (
 id uuid primary key,
 full_name varchar(100) not null
);

-- Создание таблицы для хранения информации о контактах клиентов
create table if not exists contacts (
  id uuid primary key,
  client_id uuid references clients(id),
  type varchar(20) not null,
  value varchar(100) not null
        constraint contact_value_constr unique
);