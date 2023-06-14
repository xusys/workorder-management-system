create table area
(
    id       char(4)     not null comment '主键'
        primary key,
    city     varchar(50) null comment '城市名',
    district varchar(50) null comment '区（县）名'
);


insert into area value ('0000', '广东省总部', null);

insert into area value ('0100', '广州市总部', null);
insert into area value ('0101', '广州市', '越秀区');
insert into area value ('0102', '广州市', '海珠区');
insert into area value ('0103', '广州市', '荔湾区');
insert into area value ('0104', '广州市', '天河区');
insert into area value ('0105', '广州市', '白云区');
insert into area value ('0106', '广州市', '黄埔区');
insert into area value ('0107', '广州市', '花都区');
insert into area value ('0108', '广州市', '番禺区');
insert into area value ('0109', '广州市', '南沙区');
insert into area value ('0110', '广州市', '从化区');
insert into area value ('0111', '广州市', '增城区');

insert into area value ('0200', '深圳市总部', null);
insert into area value ('0201', '深圳市', '福田区');
insert into area value ('0202', '深圳市', '罗湖区');
insert into area value ('0203', '深圳市', '盐田区');
insert into area value ('0204', '深圳市', '南山区');
insert into area value ('0205', '深圳市', '宝安区');
insert into area value ('0206', '深圳市', '龙岗区');
insert into area value ('0207', '深圳市', '龙华区');
insert into area value ('0208', '深圳市', '坪山区');
insert into area value ('0209', '深圳市', '光明区');

insert into area value ('0300', '珠海市总部', null);
insert into area value ('0301', '珠海市', '香洲区');
insert into area value ('0302', '珠海市', '金湾区');
insert into area value ('0303', '珠海市', '斗门区');

insert into area value ('0400', '汕头市总部', null);
insert into area value ('0401', '汕头市', '金平区');
insert into area value ('0402', '汕头市', '龙湖区');
insert into area value ('0403', '汕头市', '澄海区');
insert into area value ('0404', '汕头市', '濠江区');
insert into area value ('0405', '汕头市', '潮阳区');
insert into area value ('0406', '汕头市', '潮南区');
insert into area value ('0407', '汕头市', '南澳县');

insert into area value ('0500', '佛山市总部', null);
insert into area value ('0501', '佛山市', '禅城区');
insert into area value ('0502', '佛山市', '南海区');
insert into area value ('0503', '佛山市', '顺德区');
insert into area value ('0504', '佛山市', '高明区');
insert into area value ('0505', '佛山市', '三水区');

insert into area value ('0600', '韶关市总部', null);
insert into area value ('0601', '韶关市', '浈江区');
insert into area value ('0602', '韶关市', '武江区');
insert into area value ('0603', '韶关市', '曲江区');
insert into area value ('0604', '韶关市', '乐昌市');
insert into area value ('0605', '韶关市', '南雄市');
insert into area value ('0606', '韶关市', '仁化县');
insert into area value ('0607', '韶关市', '始兴市');
insert into area value ('0608', '韶关市', '翁源县');
insert into area value ('0609', '韶关市', '新丰县');
insert into area value ('0610', '韶关市', '乳源瑶族自治县');

insert into area value ('0700', '河源市总部', null);
insert into area value ('0701', '河源市', '源城区');
insert into area value ('0702', '河源市', '东源县');
insert into area value ('0703', '河源市', '和平县');
insert into area value ('0704', '河源市', '龙川县');
insert into area value ('0705', '河源市', '紫金县');
insert into area value ('0706', '河源市', '连平县');

insert into area value ('0800', '梅州市总部', null);
insert into area value ('0801', '梅州市', '梅江区');
insert into area value ('0802', '梅州市', '梅县区');
insert into area value ('0803', '梅州市', '兴宁市');
insert into area value ('0804', '梅州市', '平远县');
insert into area value ('0805', '梅州市', '蕉岭县');
insert into area value ('0806', '梅州市', '大埔县');
insert into area value ('0807', '梅州市', '丰顺县');
insert into area value ('0808', '梅州市', '五华县');

insert into area value ('0900', '惠州市总部', null);
insert into area value ('0901', '惠州市', '惠城区');
insert into area value ('0902', '惠州市', '惠阳区');
insert into area value ('0903', '惠州市', '惠东县');
insert into area value ('0904', '惠州市', '博罗县');
insert into area value ('0905', '惠州市', '龙门县');

insert into area value ('1000', '汕尾市总部', null);
insert into area value ('1001', '汕尾市', '城区');
insert into area value ('1002', '汕尾市', '陆丰市');
insert into area value ('1003', '汕尾市', '海丰县');
insert into area value ('1004', '汕尾市', '陆河县');

insert into area value ('1100', '东莞市总部', null);

insert into area value ('1200', '中山市总部', null);

insert into area value ('1300', '江门市总部', null);
insert into area value ('1301', '江门市', '蓬江区');
insert into area value ('1302', '江门市', '江海区');
insert into area value ('1303', '江门市', '新会区');
insert into area value ('1304', '江门市', '台山市');
insert into area value ('1305', '江门市', '开平市');
insert into area value ('1306', '江门市', '鹤山市');
insert into area value ('1307', '江门市', '恩平市');

insert into area value ('1400', '阳江市总部', null);
insert into area value ('1401', '阳江市', '江城区');
insert into area value ('1402', '阳江市', '阳东区');
insert into area value ('1403', '阳江市', '阳春区');
insert into area value ('1404', '阳江市', '阳西县');

insert into area value ('1500', '湛江市总部', null);
insert into area value ('1501', '湛江市', '赤坎区');
insert into area value ('1502', '湛江市', '霞山区');
insert into area value ('1503', '湛江市', '麻章区');
insert into area value ('1504', '湛江市', '坡头区');
insert into area value ('1505', '湛江市', '雷州市');
insert into area value ('1506', '湛江市', '廉江市');
insert into area value ('1507', '湛江市', '吴川市');
insert into area value ('1508', '湛江市', '逐溪县');
insert into area value ('1509', '湛江市', '徐闻县');

insert into area value ('1600', '茂名市总部', null);
insert into area value ('1601', '茂名市', '茂南区');
insert into area value ('1602', '茂名市', '电白区');
insert into area value ('1603', '茂名市', '信宜市');
insert into area value ('1604', '茂名市', '高州市');
insert into area value ('1605', '茂名市', '化州市');

insert into area value ('1700', '肇庆市总部', null);
insert into area value ('1701', '肇庆市', '端州区');
insert into area value ('1702', '肇庆市', '鼎湖区');
insert into area value ('1703', '肇庆市', '高要区');
insert into area value ('1704', '肇庆市', '四会市');
insert into area value ('1705', '肇庆市', '广宁县');
insert into area value ('1706', '肇庆市', '德庆县');
insert into area value ('1707', '肇庆市', '封开县');
insert into area value ('1708', '肇庆市', '怀集县');

insert into area value ('1800', '清远市总部', null);
insert into area value ('1801', '清远市', '清城区');
insert into area value ('1802', '清远市', '清新区');
insert into area value ('1803', '清远市', '英德市');
insert into area value ('1804', '清远市', '连州市');
insert into area value ('1805', '清远市', '佛冈县');
insert into area value ('1806', '清远市', '阳山县');
insert into area value ('1807', '清远市', '连山壮族瑶族自治县');
insert into area value ('1808', '清远市', '连南瑶族自治县');

insert into area value ('1900', '潮州市总部', null);
insert into area value ('1901', '潮州市', '湘桥区');
insert into area value ('1902', '潮州市', '潮安区');
insert into area value ('1903', '潮州市', '饶平县');

insert into area value ('2000', '揭阳市总部', null);
insert into area value ('2001', '揭阳市', '榕城区');
insert into area value ('2002', '揭阳市', '揭东区');
insert into area value ('2003', '揭阳市', '普宁市');
insert into area value ('2004', '揭阳市', '揭西县');
insert into area value ('2005', '揭阳市', '惠来县');

insert into area value ('2100', '云浮市总部', null);
insert into area value ('2101', '云浮市', '云城区');
insert into area value ('2102', '云浮市', '云安区');
insert into area value ('2103', '云浮市', '罗定市');
insert into area value ('2104', '云浮市', '新兴县');
insert into area value ('2105', '云浮市', '郁南县');


