-- tb_member
INSERT INTO tb_member (member_id, password, nickname) VALUES
('user001', 'password123', '홍길동'),
('admin', 'admin123', '관리자');

-- tb_item
-- 얼굴
INSERT INTO tb_item (item_id, type, name, price, color) VALUES
('default', 'FACE', '기본', 0, 'default'),
('cute', 'FACE', '귀여움', 100, 'pink'),
('cool', 'FACE', '멋짐', 100, 'blue');

-- 헤어
INSERT INTO tb_item (item_id, type, name, price, color) VALUES
('short_brown', 'HAIR', '짧은 갈색', 0, 'brown'),
('short_black', 'HAIR', '짧은 검정', 150, 'black'),
('short_blonde', 'HAIR', '짧은 금발', 150, 'blonde'),
('long_brown', 'HAIR', '긴 갈색', 200, 'brown'),
('long_black', 'HAIR', '긴 검정', 200, 'black'),
('pomade_black', 'HAIR', '포마드 검정', 250, 'black'),
('pomade_brown', 'HAIR', '포마드 갈색', 250, 'brown'),
('gray', 'HAIR', '회색', 300, 'gray');

-- 상의
INSERT INTO tb_item (item_id, type, name, price, color) VALUES
('tshirt_white', 'TOP', '흰색 티셔츠', 0, 'white'),
('tshirt_blue', 'TOP', '파란 티셔츠', 100, 'blue'),
('tshirt_red', 'TOP', '빨간 티셔츠', 100, 'red'),
('tshirt_green', 'TOP', '초록 티셔츠', 100, 'green'),
('tshirt_flower', 'TOP', '꽃무늬 티셔츠', 200, 'flower'),
('shirt_white', 'TOP', '흰색 셔츠', 250, 'white');

-- 하의
INSERT INTO tb_item (item_id, type, name, price, color) VALUES
('pants_black', 'BOTTOM', '검정 바지', 0, 'black'),
('pants_navy', 'BOTTOM', '네이비 바지', 150, 'navy'),
('pants_gray', 'BOTTOM', '회색 바지', 150, 'gray'),
('jeans_blue', 'BOTTOM', '파란 청바지', 200, 'blue');

-- 신발
INSERT INTO tb_item (item_id, type, name, price, color) VALUES
('shoes_black', 'SHOES', '검정 구두', 0, 'black'),
('shoes_brown', 'SHOES', '갈색 구두', 150, 'brown'),
('sneakers_white', 'SHOES', '흰색 운동화', 200, 'white'),
('sneakers_black', 'SHOES', '검정 운동화', 200, 'black');

-- tb_member_equipped_item
INSERT INTO tb_member_equipped_item (member_id, face_item_id, hair_item_id, top_item_id, bottom_item_id, shoes_item_id) VALUES
('user001', 'default', 'short_brown', 'tshirt_white', 'pants_black', 'shoes_black'),
('admin', 'default', 'short_brown', 'tshirt_white', 'pants_black', 'shoes_black');