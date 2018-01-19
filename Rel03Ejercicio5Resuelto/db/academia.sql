drop table alumno_asignatura;
drop table asignatura;
drop table alumno;
drop table profesor;
drop table persona;



create table persona (
dni varchar(11) primary key,
Nombre varchar(30),
Apellido varchar(30),
Ciudad varchar(15),
Direccioncalle varchar(30),
Direccionnum int,
Telefono varchar(9));



create table alumno (
idalumno varchar(7) primary key,
dni varchar(11),
foreign key (dni) references persona(dni)
);

Create table profesor (
Idprofesor varchar(4) primary key,
Dni varchar(11),
Foreign key(dni) references persona(dni)
);

Create table asignatura (
Idasignatura varchar(6) primary key,
Nombre varchar(20),
Creditos int,
Cuatrimestre int,
Costebasico int,
Idprofesor varchar(4),
Curso int,
Foreign key(idprofesor) references profesor(idprofesor)
);

Create table  alumno_asignatura (
Idalumno varchar(7),
Idasignatura varchar(6),
Numeromatricula int,
  PRIMARY KEY(idalumno,idasignatura,numeromatricula),
Foreign key(idalumno) references alumno(idalumno),
Foreign key(idasignatura) references asignatura(idasignatura)
);


insert into persona values ('16161616A','Luis','Ramirez','Haro','Pez','34','941111111');
insert into persona values ('17171717A','Laura','Beltran','Madrid','Gran Va','23','912121212');
insert into persona values ('18181818A','Pepe','Perez','Madrid','Percebe','13','913131313');
insert into persona values ('19191919A','Juan','Sanchez','Bilbao','Melancola','7','944141414');
insert into persona values ('20202020A','Luis','Jimenez','Najera','Cigea','15','941151515');
insert into persona values ('21212121A','Rosa','Garcia','Haro','Alegra','16','941161616');
insert into persona values ('23232323A','Jorge','Saenz','Sevilla','Luis Ulloa','17','941171717');
insert into persona values ('24242424A','Mara','Gutierrez','Sevilla','Avda. de la Paz','18','941181818');
insert into persona values ('25252525A','Rosario','Diaz','Sevilla','Percebe','19','941191919');
insert into persona values ('26262626A','Elena','Gonzalez','Sevilla','Percebe','20','941202020');

insert into alumno values ('A010101','21212121A');
insert into alumno values ('A020202','18181818A');
insert into alumno values ('A030303','20202020A');
insert into alumno values ('A040404','26262626A');
insert into alumno values ('A121212','16161616A');
insert into alumno values ('A131313','17171717A');

insert into profesor values ('P101','19191919A');
insert into profesor values ('P117','25252525A');
insert into profesor values ('P203','23232323A');
insert into profesor values ('P204','26262626A');
insert into profesor values ('P304','24242424A');


insert into asignatura values ('000115','Seguridad Vial','4','1','30 ','P204',null);
insert into asignatura values ('130113','Programacion I', '9','1','60 ','P101','1');
insert into asignatura values ('130122','Analisis II',    '9','2','60 ','P203','2');
insert into asignatura values ('150212','Quimica Fisica','4','2','70','P304','1');
insert into asignatura values ('160002','Contabilidad','6','1','70','P117','1');


insert into alumno_asignatura values('A010101','150212','1');
insert into alumno_asignatura values('A020202','130113','1');
insert into alumno_asignatura values('A020202','150212','2');
insert into alumno_asignatura values('A030303','130113','3');
insert into alumno_asignatura values('A030303','150212','1');
insert into alumno_asignatura values('A030303','130122','2');
insert into alumno_asignatura values('A040404','130122','1');
insert into alumno_asignatura values('A121212','000115','1');
insert into alumno_asignatura values('A131313','160002','4');

