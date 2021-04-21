--liquibase formatted sql
--changeset matheus.dias:1
DROP TABLE IF EXISTS tasks;
CREATE TABLE  tasks (
  id NUMERIC(19) NOT NULL,
  title VARCHAR(40) NOT NULL,
  status SMALLINT NOT NULL DEFAULT 0,
  description VARCHAR(500),
  CONSTRAINT PK_TASKS PRIMARY KEY (id)
);
--rollback drop table tasks;