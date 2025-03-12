-- Create the offers table.
CREATE TABLE offer (
    offer_id SERIAL PRIMARY KEY,
    brand_id INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    partnumber VARCHAR(50) NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    curr VARCHAR(10) NOT NULL
);

-- Create indexes to optimize queries.
CREATE INDEX idx_offer_brand ON offer(brand_id);
CREATE INDEX idx_offer_partnumber ON offer(partnumber);
CREATE INDEX idx_offer_start_end_date ON offer(start_date, end_date);