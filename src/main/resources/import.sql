INSERT INTO p_patient (id, p_active, p_deceasedboolean, p_gender) VALUES ('00000000-0000-0000-0000-000000000000', true, false, 'male');
INSERT INTO p_patient (id, p_active, p_deceasedboolean, p_gender) VALUES ('00000000-0000-0000-0000-000000000001', true, false, 'female');

-- 1:1 Beziehungen mit dem Patienten müssen vor dem Patienten eingefügt werden.
INSERT INTO p_patient (id, p_active, p_deceasedboolean, p_gender) VALUES ('00000000-0000-0000-0000-000000000002', false, true, 'other');
-- 1:n Beziehungen mit dem Patienten müssen nach dem Patienten eingefügt werden
INSERT INTO i_identifier (id, i_system, i_use, i_value, i_p_id) VALUES ('00000000-0000-0000-0000-000000000003', 'urn:oid:1.2.36.146.595.217.0.1', 'usual', '12345', '00000000-0000-0000-0000-000000000002');
INSERT INTO hn_humanname (id, hn_family, hn_use, hn_pe_id, hn_p_id) VALUES ('00000000-0000-0000-0000-000000000004', 'Pirker', 'official', NULL, '00000000-0000-0000-0000-000000000002');
INSERT INTO hn_humanname (id, hn_family, hn_use, hn_pe_id, hn_p_id) VALUES ('00000000-0000-0000-0000-000000000005', 'Pirker', 'usual', NULL, '00000000-0000-0000-0000-000000000002');
INSERT INTO given_humanname (id, given) VALUES ('00000000-0000-0000-0000-000000000005','Simon');
INSERT INTO given_humanname (id, given) VALUES ('00000000-0000-0000-0000-000000000005','Kein weiterer');
INSERT INTO prefix_humanname (id, prefix) VALUES ('00000000-0000-0000-0000-000000000005','Ing.');
INSERT INTO prefix_humanname (id, prefix) VALUES ('00000000-0000-0000-0000-000000000005','Dipl.Ing.');
INSERT INTO prefix_humanname (id, prefix) VALUES ('00000000-0000-0000-0000-000000000005','Mag.');
INSERT INTO prefix_humanname (id, prefix) VALUES ('00000000-0000-0000-0000-000000000005','Dr.phil.');
INSERT INTO prefix_humanname (id, prefix) VALUES ('00000000-0000-0000-0000-000000000005','Dr.');
INSERT INTO suffix_humanname (id, suffix) VALUES ('00000000-0000-0000-0000-000000000005','Bakk');
INSERT INTO suffix_humanname (id, suffix) VALUES ('00000000-0000-0000-0000-000000000005','MSc');
-- Insert für telecom
INSERT INTO pe_period (id, pe_end, pe_start) VALUES ('00000000-0000-0000-0000-000000000006', '2040-01-05', '1999-01-01');
INSERT INTO cp_contactpoint (id, cp_rank, cp_system, cp_use, cp_value, cp_p_id, cp_pe_id) VALUES ('00000000-0000-0000-0000-000000000007', '1', 'phone', 'work', '015552231123', '00000000-0000-0000-0000-000000000002','00000000-0000-0000-0000-000000000006');
INSERT INTO cp_contactpoint (id, cp_rank, cp_system, cp_use, cp_value, cp_p_id) VALUES ('00000000-0000-0000-0000-000000000008', '2', 'email', 'work', 'pirker@spengergasse.at', '00000000-0000-0000-0000-000000000002');
--Insert für address
INSERT INTO pe_period (id, pe_end, pe_start) VALUES ('00000000-0000-0000-0000-000000000009', '2040-01-05', '1999-01-01');
INSERT INTO a_address (id, a_city, a_country, a_district,  a_postalcode, a_state, a_type, a_use, a_p_id, a_pe_id) VALUES ('00000000-0000-0000-0000-000000000010', 'Wien', 'Österreich', 'Wien', '1050', 'Wien', 'both', 'home', '00000000-0000-0000-0000-000000000002','00000000-0000-0000-0000-000000000009');
INSERT INTO a_address_line (address_id, line) VALUES ('00000000-0000-0000-0000-000000000010','Simon Pirker');
INSERT INTO a_address_line (address_id, line) VALUES ('00000000-0000-0000-0000-000000000010','Spengergasse 20');
INSERT INTO a_address_line (address_id, line) VALUES ('00000000-0000-0000-0000-000000000010','1050 Wien');