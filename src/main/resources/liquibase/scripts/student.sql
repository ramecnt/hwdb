-- liquibase formatted sql

-- changeset kolya: 1
CREATE INDEX student_index ON public.student (name);