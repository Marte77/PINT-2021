drop table if exists public."Alertas";
drop table if exists public."Report_Indoors";
drop table if exists public."Report_Outdoor_Outros_Utils";
drop table if exists public."Report_Outdoor_Util_Instituicaos";
drop table if exists public."Local_Indoors";
drop table if exists public."Comentarios";
drop table if exists public."List_LocalFavoritos";
drop table if exists public."Locals";
drop table if exists public."Outros_Utils";
drop table if exists public."Util_pertence_Insts";
drop table if exists public."Utils_Instituicaos";
drop table if exists public."Admins";
drop table if exists public."Instituicaos";
drop table if exists public."Tabela_LikesDislikes";
drop table if exists public."Lista_Favoritos";
drop table if exists public."Pessoas";
drop table if exists public."Tipo_Alertas";
drop table if exists public."Reports";

--selects
select * from public."Pessoas";
select * from public."Outros_Utils";
select * from public."Utils_Instituicaos";
select * from public."Reports";
select * from public."Tabela_LikesDislikes"
--delete from public."Tabela_LikesDislikes"
--update public."Pessoas" set "Email" ='martinhoantigo' where "Email" ='martinho';
--delete from public."Pessoas" where "IDPessoa" >= 38;
--delete from public."Outros_Utils" where "PessoaIDPessoa" >= 38;
--update public."Utils_Instituicaos" set "Verificado" ='true' where "ID_Util" ='13';

select * from "Report_Outdoor_Outros_Utils";
select * from "Report_Outdoor_Util_Instituicaos";
select "Data" from "Report_Outdoor_Outros_Utils" inner join "Reports"
on "Reports"."ID_Report" ="Report_Outdoor_Outros_Utils"."ReportIDReport";
select "Data" from "Report_Outdoor_Util_Instituicaos" inner join "Reports"
on "Reports"."ID_Report" = "Report_Outdoor_Util_Instituicaos"."ReportIDReport";