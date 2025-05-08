# Hệ Thống Gợi Ý Từ và Kiểm Tra Chính Tả

## Giới thiệu
Dự án này là một hệ thống gợi ý từ (autocomplete) và kiểm tra chính tả (spell checking) được xây dựng bằng Java Spring Boot. Hệ thống sử dụng hai thuật toán chính là Levenshtein Distance và Ternary Search Tree để cung cấp khả năng gợi ý từ và sửa lỗi chính tả hiệu quả.

## Các Thuật Toán Được Sử Dụng

### 1. Levenshtein Distance
- **Ý tưởng**: Đây là thuật toán đo khoảng cách chỉnh sửa giữa hai chuỗi ký tự, tính toán số phép biến đổi tối thiểu (thêm, xóa, thay thế) cần thiết để chuyển từ chuỗi này sang chuỗi khác.
- **Ứng dụng**: Được sử dụng để tìm các từ gần đúng với từ người dùng nhập vào, giúp gợi ý các từ thay thế khi có lỗi chính tả.

#### Sơ đồ khối thuật toán Levenshtein Distance:
```
┌─────────────────────────────────────────────────────────────┐
│                     Bắt đầu                                 │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Khởi tạo ma trận DP với kích thước (len1+1) x (len2+1)     │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Khởi tạo hàng đầu tiên và cột đầu tiên của ma trận         │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Duyệt qua từng ô trong ma trận:                            │
│  - Nếu ký tự giống nhau: cost = 0                          │
│  - Nếu ký tự khác nhau: cost = 1                           │
│  - dp[i][j] = min(dp[i-1][j] + 1,                          │
│                   dp[i][j-1] + 1,                          │
│                   dp[i-1][j-1] + cost)                     │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Trả về giá trị dp[len1][len2]                             │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                     Kết thúc                                │
└─────────────────────────────────────────────────────────────┘
```

### 2. Ternary Search Tree (TST)
- **Ý tưởng**: Là cấu trúc dữ liệu cây tìm kiếm ba nhánh, mỗi nút có thể có tối đa ba con (trái, giữa, phải). TST kết hợp ưu điểm của cây nhị phân và cây tiền tố.
- **Ứng dụng**: Được sử dụng để lưu trữ và tìm kiếm từ điển hiệu quả, hỗ trợ tính năng gợi ý từ dựa trên tiền tố.

#### Sơ đồ khối thuật toán TST:

```
┌─────────────────────────────────────────────────────────────┐
│                     Bắt đầu                                 │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Chèn từ vào TST:                                          │
│  - Nếu nút hiện tại null: tạo nút mới                      │
│  - So sánh ký tự hiện tại:                                 │
│    + Nếu nhỏ hơn: đi sang nhánh trái                       │
│    + Nếu lớn hơn: đi sang nhánh phải                       │
│    + Nếu bằng: đi sang nhánh giữa                          │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Tìm kiếm từ:                                              │
│  - Duyệt cây theo ký tự                                    │
│  - Nếu đến cuối từ và nút đánh dấu kết thúc: từ tồn tại   │
│  - Nếu không tìm thấy: từ không tồn tại                    │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│  Tìm kiếm tiền tố:                                         │
│  - Tìm nút cuối của tiền tố                                │
│  - Thu thập tất cả từ bắt đầu từ nút đó                    │
└───────────────────────────┬─────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                     Kết thúc                                │
└─────────────────────────────────────────────────────────────┘
```
## Cài Đặt Database
1. Di chuyển đến thư mục data:
```bash
cd src\main\java\hus\daa\app\data
```
2. Chạy file docker-compose.yml:
```bash
docker-compose up -d
```
3. Kiểm tra kết nối đến database:
```
Có thể dùng Dbear chọn postgresql và kết nối đến localhost:5432
username: daa
password: daa123
```

## Cài Đặt

### Yêu Cầu Hệ Thống
- Java JDK 17 trở lên
- Maven 3.6.x trở lên
- IDE (khuyến nghị IntelliJ IDEA hoặc Eclipse)

### Các Bước Cài Đặt
1. Clone repository:
```bash
git clone https://github.com/your-username/Autocomplete-and-Spell-Checking.git
```

2. Di chuyển vào thư mục dự án:
```bash
cd Autocomplete-and-Spell-Checking
```

3. Build project bằng Maven:
```bash
mvn clean install
```

4. Chạy ứng dụng:
```bash
mvn spring-boot:run
```

## Cách Sử Dụng

### API Endpoints

1. Gợi ý từ:
```
GET api/words/autocomplete?prefix=chuẩn&limit=10
```
Response:
```json
{
    "prefix": "chuẩn",
    "suggestions": [
        "chuẩn",
        "chuẩn bị",
        "chuẩn chi",
        "chuẩn cấp",
        "chuẩn cứ",
        "chuẩn gốc",
        "chuẩn hoá",
        "chuẩn mực",
        "chuẩn mực hoá",
        "chuẩn nhận"
    ]
}
```

2. Kiểm tra chính tả:
```
GET api/words/spellcheck?word=ckung te&maxDistance=4&limit=20
```
Response:
```json
{
    "suggestions": [
        "chăng tá",
        "chổng tĩ",
        "chung cư",
        "chung kì",
        "chúng ta",
        "chúng tớ",
        "chứng tá",
        "chứng tỏ",
        "chứng từ",
        "công ti",
        "công tố",
        "công tư",
        "công tử",
        "công ty",
        "cung độ",
        "cung hạ",
        "cung hỉ",
        "cung mê",
        "cung nữ",
        "cung tần"
    ],
    "word": "ckung te",
    "isCorrect": false
}
```

## Điều Kiện Tiên Quyết

### Kiến Thức Cần Có
- Java Programming
- Spring Boot Framework
- Cấu trúc dữ liệu và thuật toán cơ bản
- RESTful API

### Công Cụ Cần Thiết
- Git
- Maven
- IDE (IntelliJ IDEA/Eclipse)
- Postman hoặc công cụ tương tự để test API

## Đóng Góp
Mọi đóng góp đều được hoan nghênh! Vui lòng tạo issue hoặc pull request để đóng góp vào dự án.