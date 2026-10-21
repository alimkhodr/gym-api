CREATE TABLE IF NOT EXISTS plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    monthly_price DECIMAL(10, 2) NOT NULL,
    duration_months INT NOT NULL
);

CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    plan_id BIGINT NOT NULL REFERENCES plans (id)
);

CREATE TABLE IF NOT EXISTS instructors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    specialty VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS gym_classes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    schedule VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    instructor_id BIGINT NOT NULL REFERENCES instructors (id)
);

CREATE TABLE IF NOT EXISTS equipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS class_members (
    class_id BIGINT NOT NULL REFERENCES gym_classes (id) ON DELETE CASCADE,
    member_id BIGINT NOT NULL REFERENCES members (id) ON DELETE CASCADE,
    PRIMARY KEY (class_id, member_id)
);

CREATE TABLE IF NOT EXISTS class_equipment (
    class_id BIGINT NOT NULL REFERENCES gym_classes (id) ON DELETE CASCADE,
    equipment_id BIGINT NOT NULL REFERENCES equipment (id) ON DELETE CASCADE,
    PRIMARY KEY (class_id, equipment_id)
);
