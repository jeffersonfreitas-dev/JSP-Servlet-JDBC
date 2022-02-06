create table login (
	login character varying(100),
	senha character varying(100),
	constraint uni_login unique (login)
);