CREATE SEQUENCE tr_currency_id_seq INCREMENT BY 1 START WITH 1;
CREATE table tr_currency
(
    id    bigint default nextval('tr_currency_id_seq') primary key,
    base  varchar(16)    not null,
    rate  varchar(16)    not null,
    exchange_rate numeric(16, 6) not null,
    exchange_date  timestamp   not null
);
