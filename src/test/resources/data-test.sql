CREATE TABLE IF NOT EXISTS exchanger_response (
                                                  id serial primary key,
                                                  base varchar(16),
                                                  rate varchar(16),
                                                  value numeric(16,6),
                                                  date timestamp(0)
);