alter table medicos add activo tinyint;
update medicos set activo = 1; -- actualizar registros existentes