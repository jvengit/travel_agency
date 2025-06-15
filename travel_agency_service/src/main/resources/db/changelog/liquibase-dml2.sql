--liquibase formatted sql

--changeset liquibase-docs:sql-3
INSERT INTO travel_arrangement(agent, client, flight_id, booking_id, status) 
VALUES ('asd', 'zxc', 1, 1, 1);
INSERT INTO travel_arrangement(agent, client, flight_id, booking_id, status) 
VALUES ('qwe', 'zxc', 1, 1, 1);
INSERT INTO travel_arrangement(agent, client, flight_id, booking_id, status) 
VALUES ('zxc', 'zxc', 1, 1, 1);
INSERT INTO travel_arrangement(agent, client, flight_id, booking_id, status) 
VALUES ('cxz', 'zxc', 1, 1, 1);
