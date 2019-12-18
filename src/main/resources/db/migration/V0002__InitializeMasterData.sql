INSERT INTO tbl_role
VALUES (NEXTVAL('hibernate_sequence'), 'GLOBALADMIN', 'Global Administrator'),
       (NEXTVAL('hibernate_sequence'), 'ADMIN', 'Shop Administrator'),
       (NEXTVAL('hibernate_sequence'), 'SELLER', 'Seller'),
       (NEXTVAL('hibernate_sequence'), 'SUPPORT', 'Support'),
       (NEXTVAL('hibernate_sequence'), 'BUYER', 'Buyer');
