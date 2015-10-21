----------------------------------------------------------------
-- RECETTE + PROD
----------------------------------------------------------------
ALTER TABLE SIRH.FICHE_POSTE ALTER COLUMN ID_SERVI SET DATA TYPE CHAR(16);
ALTER TABLE SIRH.HISTO_FICHE_POSTE ALTER COLUMN ID_SERVI SET DATA TYPE CHAR(16);
