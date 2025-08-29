---- Table: Member
--CREATE TABLE Member (
--    username VARCHAR(255) PRIMARY KEY,
--    password VARCHAR(255)
--);
--
---- Table: InfoList
--CREATE TABLE InfoList (
--    order_id VARCHAR(255) PRIMARY KEY,     -- or INT if preferred
--    dairy_info TEXT,
--    time_stamp VARCHAR(50),
--    boolean_check BOOLEAN,
--    username VARCHAR(255),
--
--    -- Define foreign key constraint
--    CONSTRAINT fk_user FOREIGN KEY (username)
--        REFERENCES Member(username)
--        ON DELETE CASCADE      -- Optional: delete related InfoList if user is deleted
--);
-- Drop tables if they exist (ระวัง foreign key constraint)
--DROP TABLE IF EXISTS ListInfo CASCADE;
--DROP TABLE IF EXISTS Member CASCADE;

-- Table: Member
CREATE TABLE IF NOT EXISTS Member (
  username VARCHAR(255) PRIMARY KEY,
  password VARCHAR(255) NOT NULL
);




-- Table: InfoList
CREATE TABLE ListInfo (
    order_id VARCHAR(255),
    dairy_info TEXT,
    time_stamp VARCHAR(50),
    boolean_check BOOLEAN,
    username VARCHAR(255),

    -- Define foreign key constraint
    CONSTRAINT fk_user FOREIGN KEY (username)
        REFERENCES Member(username)
        ON DELETE CASCADE
);