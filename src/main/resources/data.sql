-- tb_member
INSERT INTO tb_member (member_id, password, nickname) VALUES
('user001', 'password123', '홍길동'),
('admin', 'admin123', '관리자');

-- tb_item
-- 얼굴
INSERT INTO tb_item (item_id, type, name, price) VALUES
('default', 'face', '기본', 0),
('cute', 'face', '귀여움', 100),
('cool', 'face', '멋짐', 100);

-- 헤어
INSERT INTO tb_item (item_id, type, name, price) VALUES
('short_brown', 'hair', '짧은 갈색', 0),
('short_black', 'hair', '짧은 검정', 150),
('short_blonde', 'hair', '짧은 금발', 150),
('long_brown', 'hair', '긴 갈색', 200),
('long_black', 'hair', '긴 검정', 200),
('pomade_black', 'hair', '포마드 검정', 250),
('pomade_brown', 'hair', '포마드 갈색', 250),
('gray', 'hair', '회색', 300);

-- 상의
INSERT INTO tb_item (item_id, type, name, price) VALUES
('tshirt_white', 'top', '흰색 티셔츠', 0),
('tshirt_blue', 'top', '파란 티셔츠', 100),
('tshirt_red', 'top', '빨간 티셔츠', 100),
('tshirt_green', 'top', '초록 티셔츠', 100),
('tshirt_flower', 'top', '꽃무늬 티셔츠', 200),
('shirt_white', 'top', '흰색 셔츠', 250);

-- 하의
INSERT INTO tb_item (item_id, type, name, price) VALUES
('pants_black', 'bottom', '검정 바지', 0),
('pants_navy', 'bottom', '네이비 바지', 150),
('pants_gray', 'bottom', '회색 바지', 150),
('jeans_blue', 'bottom', '파란 청바지', 200);

-- 신발
INSERT INTO tb_item (item_id, type, name, price) VALUES
('shoes_black', 'shoes', '검정 구두', 0),
('shoes_brown', 'shoes', '갈색 구두', 150),
('sneakers_white', 'shoes', '흰색 운동화', 200),
('sneakers_black', 'shoes', '검정 운동화', 200);