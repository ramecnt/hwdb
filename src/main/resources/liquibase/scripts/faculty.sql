-- liquibase formatted sql

-- changeset kolya: 1
CREATE INDEX faculty_index ON public.faculty (name, color);