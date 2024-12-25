CREATE TABLE notice (
          id SERIAL PRIMARY KEY,
          message TEXT,
          type VARCHAR(50),
          processed BOOLEAN
);