CREATE TABLE public.tb_user (
	id serial NOT NULL,
	ds_login varchar(30) NULL,
	ds_password varchar(1000) NULL,
	ds_name varchar (100) not null,
	ds_last_name VARCHAR (100) not null,
	document_id VARCHAR (250),
	CONSTRAINT tb_user_pkey PRIMARY KEY (id)
);

ALTER TABLE PUBLIC.TB_USER ADD COLUMN DT_PROCESS TIMESTAMP DEFAULT NOW();