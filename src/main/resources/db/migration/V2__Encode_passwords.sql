create extension if not exists pgcrypto WITH SCHEMA kenguru;

update kenguru.users set password = kenguru.crypt(password, kenguru.gen_salt('bf', 8));