INSERT INTO v_feature (id, category, description, name) VALUES (1, 3, 'Anti-blocking system helps you to crash slower', 'ABS');
INSERT INTO v_feature (id, category, description, name) VALUES (2, 4, 'Turns windscreen wipers on when you least expect it', 'Rain Sensor');
INSERT INTO v_feature (id, category, description, name) VALUES (3, 0, null, 'Sport Seats');
INSERT INTO v_feature (id, category, description, name) VALUES (4, 0, 'Destroys your battery 2x faster', 'Start-Stop');

-- TODO: Move descriptions and features to vehicle-discovery

--

INSERT INTO v_gallery (id, V_OFFER, title, url) VALUES (1, 1, 'Front image', '/images/car?type=hatchback&color=%23b52828');
INSERT INTO v_gallery (id, V_OFFER, title, url) VALUES (2, 1, 'Rear image', '/images/car?type=sedan&color=%23b52828');

INSERT INTO v_offer (id, drive, emissions, engine, fuel, make, model, seats, trany, vclass, year, colordescription, history, inspectionvaliduntil, mileage, prevowners, rgbcolor, trimlevel, mainimage_id) VALUES (1, 'Front-Wheel Drive', 'EURO6a', '2.0 CDTi, 123kW', 'Diesel', 'ACME Cars', 'Fooryon', 5, 'Manual 6-speed', 'hatchback', 2018, 'Dark Red', 'Well clean is as good as new', '2021-06-27 00:00:00', 12345, 1, '0099FF', 'Barneo', 1);

INSERT INTO v_offer_v_feature (features_id, vehicleoffer_id) VALUES (1, 1), (2, 1), (3, 1), (4, 1);

--

INSERT INTO v_gallery (id, V_OFFER, title, url) VALUES (3, 2, 'See this beast!', '/images/car?type=supercar&color=%23ff9cfa');

INSERT INTO v_offer (id, drive, emissions, engine, fuel, make, model, seats, trany, vclass, year, colordescription, history, inspectionvaliduntil, mileage, prevowners, rgbcolor, trimlevel, mainimage_id) VALUES (2, 'Rear-Wheel Drive', 'EURO1a', '4.0, 666kW', 'Gasoline', 'Diabolico', 'Daredevil', 1, 'Automatic 666-speed', 'supercar', 2012, 'Evil Pink', 'Blablabla...', '2021-09-27 00:00:00', 666666, 1, '0099FF', 'Barneo', 3);

--

ALTER SEQUENCE hibernate_sequence RESTART WITH 100;



