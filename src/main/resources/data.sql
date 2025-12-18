-- tb_member
INSERT INTO tb_member (member_id, password, nickname) VALUES
('user001', 'password123', '홍길동'),
('admin', 'admin123', '관리자');

-- tb_item
-- 얼굴
INSERT INTO tb_item (item_id, type, name, price) VALUES
('default', '얼굴', '기본', 0),
('cute', '얼굴', '귀여움', 100),
('cool', '얼굴', '멋짐', 100);

-- 헤어
INSERT INTO tb_item (item_id, type, name, price) VALUES
('short_brown', '헤어', '짧은 갈색', 0),
('short_black', '헤어', '짧은 검정', 150),
('short_blonde', '헤어', '짧은 금발', 150),
('long_brown', '헤어', '긴 갈색', 200),
('long_black', '헤어', '긴 검정', 200),
('pomade_black', '헤어', '포마드 검정', 250),
('pomade_brown', '헤어', '포마드 갈색', 250),
('gray', '헤어', '회색', 300);

-- 상의
INSERT INTO tb_item (item_id, type, name, price) VALUES
('tshirt_white', '상의', '흰색 티셔츠', 0),
('tshirt_blue', '상의', '파란 티셔츠', 100),
('tshirt_red', '상의', '빨간 티셔츠', 100),
('tshirt_green', '상의', '초록 티셔츠', 100),
('tshirt_flower', '상의', '꽃무늬 티셔츠', 200),
('shirt_white', '상의', '흰색 셔츠', 250);

-- 하의
INSERT INTO tb_item (item_id, type, name, price) VALUES
('pants_black', '하의', '검정 바지', 0),
('pants_navy', '하의', '네이비 바지', 150),
('pants_gray', '하의', '회색 바지', 150),
('jeans_blue', '하의', '파란 청바지', 200);

-- 신발
INSERT INTO tb_item (item_id, type, name, price) VALUES
('shoes_black', '신발', '검정 구두', 0),
('shoes_brown', '신발', '갈색 구두', 150),
('sneakers_white', '신발', '흰색 운동화', 200),
('sneakers_black', '신발', '검정 운동화', 200);