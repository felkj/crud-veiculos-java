CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);
INSERT INTO users (username, password, cargo) VALUES (
        'admin',
        '$2a$10$8BaCfqFBLeL3vwvuvLovXeoGjqGDxgQdTqzi3Svo74x5ur7i6ixa6', -- Ex: '$2a$10$wE47E6eJ7Y0n0l.k1K1l2e.A3b.c4d.e5f.g6h.i7j.k8l.m9n.o0p.q1r.s2t'
        'ADMIN'
    );

    INSERT INTO users (username, password, cargo) VALUES (
        'user',
        '$2a$10$dktotLPFFgoana585F6ZYuP6kDPJ19H1z/ZQLcyL1ZMhtbpc1H0Ce', -- Ex: '$2a$10$tJ9fF7gI8H0j1k.l2M2n3o.P4q.r5s.t6u.v7w.x8y.z9a.b0c.d1e.f2g'
        'USER'
    );