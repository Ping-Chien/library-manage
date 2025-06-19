# 圖書館管理系統 (Library Management System)

本專案是一個基於Spring Boot框架開發的圖書館管理系統，提供了圖書的增刪改查、借閱和歸還等功能。

## 系統架構

### 技術棧

- **後端框架**：Spring Boot 3.2.12
- **構建工具**：Maven
- **數據庫**：H2 (內存數據庫)
- **ORM框架**：Spring Data JPA
- **API文檔**：SpringDoc OpenAPI (Swagger UI)
- **編譯環境**：Java 17
- **代碼簡化工具**：Lombok
- **JSON處理**：Jackson
- **測試覆蓋工具**：JaCoCo

### 架構層次

本系統採用經典的三層架構設計：

1. **表示層 (Presentation Layer)**
   - 包含 `BookController` 處理HTTP請求
   - 處理請求參數驗證
   - 返回統一格式的API響應

2. **業務邏輯層 (Business Layer)**
   - `BookService` 實現業務邏輯
   - 處理圖書的增刪改查、借閱和歸還等核心功能
   - 處理業務規則和約束條件

3. **數據訪問層 (Data Access Layer)**
   - 通過 `BookRepository` 和 `BorrowLogRepository` 訪問數據庫
   - 使用Spring Data JPA進行ORM映射
   - 執行CRUD操作和自定義查詢

### 主要組件

1. **控制器 (Controller)**
   - `BookController`：處理圖書相關的HTTP請求

2. **服務 (Service)**
   - `BookService`：實現業務邏輯

3. **數據訪問對象 (Repository)**
   - `BookRepository`：訪問圖書信息
   - `BorrowLogRepository`：訪問借閱記錄

4. **實體 (Entity)**
   - `Book`：圖書實體
   - `BorrowLog`：借閱日誌實體

5. **數據傳輸對象 (DTO)**
   - `AddBookRequest`：添加圖書請求
   - `UpdateBookRequest`：更新圖書請求
   - `DeleteBookRequest`：刪除圖書請求
   - `QueryBookRequest`：查詢圖書請求
   - `QueryBookResponse`：查詢圖書響應
   - `BorrowBookRequest`：借閱圖書請求
   - `ReturnBookRequest`：歸還圖書請求
   - `QueryOverdueBookRequest`：查詢過期圖書請求
   - `QueryOverdueBookResponse`：查詢過期圖書響應
   - `BookInfo`：圖書信息
   - `ActionResponse`：操作響應
   - `ActionDetail`：操作詳情

6. **枚舉 (Enum)**
   - `BookStatus`：圖書狀態
   - `BorrowAction`：借閱動作
   - `ErrorCode`：錯誤代碼
   - `Language`：語言
   - `QueryType`：查詢類型
   - `ResponseCode`：響應代碼

7. **異常處理 (Exception)**
   - `ErrorException`：自定義錯誤異常
   - `ControllerExceptionHandler`：全局異常處理器

## API說明

### 圖書管理API

1. **添加圖書**
   - 路徑：`/book/add`
   - 方法：POST
   - 請求體：`AddBookRequest`
   - 響應：`ActionResponse`
   - 功能描述：添加新的圖書到系統中

2. **修改圖書**
   - 路徑：`/book/modify`
   - 方法：POST
   - 請求體：`UpdateBookRequest`
   - 響應：`ActionResponse`
   - 功能描述：更新已有圖書的信息

3. **刪除圖書**
   - 路徑：`/book/delete`
   - 方法：POST
   - 請求體：`DeleteBookRequest`
   - 響應：`ActionResponse`
   - 功能描述：從系統中刪除指定的圖書

4. **查詢圖書**
   - 路徑：`/book/query`
   - 方法：POST
   - 請求體：`QueryBookRequest`
   - 響應：`QueryBookResponse`
   - 功能描述：根據條件查詢圖書

### 借閱管理API

1. **借閱圖書**
   - 路徑：`/book/borrow`
   - 方法：POST
   - 請求體：`BorrowBookRequest`
   - 響應：`ActionResponse`
   - 功能描述：借閱指定的圖書

2. **歸還圖書**
   - 路徑：`/book/return`
   - 方法：POST
   - 請求體：`ReturnBookRequest`
   - 響應：`ActionResponse`
   - 功能描述：歸還已借閱的圖書

3. **查詢逾期圖書**
   - 路徑：`/book/overdue`
   - 方法：POST
   - 請求體：`QueryOverdueBookRequest`
   - 響應：`QueryOverdueBookResponse`
   - 功能描述：查詢所有逾期未還的圖書

### 請求和響應格式

#### 添加圖書請求 (AddBookRequest)
```json
{
  "bookIsbn": "9781234567890",
  "bookLanguage": "EN",
  "bookName": "The Great Book",
  "bookAuthor": "Author Name",
  "bookPublisher": "Publisher Name",
  "bookPubDate": "2023-01-01"
}
```

#### 查詢圖書請求 (QueryBookRequest)
```json
{
  "queryType": "ISBN",
  "queryValue": "9781234567890"
}
```

#### 查詢圖書響應 (QueryBookResponse)
```json
{
  "mwHeader": {
    "returnCode": "00",
    "returnDesc": "成功"
  },
  "bookCount": 1,
  "bookList": [
    {
      "bookIsbn": "9781234567890",
      "bookLanguage": "EN",
      "bookName": "The Great Book",
      "bookAuthor": "Author Name",
      "bookPublisher": "Publisher Name",
      "bookPubDate": "2023-01-01",
      "bookStatus": "01",
      "bookBorrowerId": null,
      "borrowDate": null
    }
  ]
}
```

#### 操作響應 (ActionResponse)
```json
{
  "mwHeader": {
    "returnCode": "00",
    "returnDesc": "成功"
  }
}
```

## 數據庫表結構

### 圖書信息表 (BOOK_INFO)

| 欄位名稱 | 欄位類型 | 長度 | 描述 | 備註 |
|---------|---------|------|------|-----|
| book_ISBN | VARCHAR | 30 | 圖書ISBN編號 | 主鍵 |
| book_language | VARCHAR | 2 | 圖書語言 | 例如：EN(英文), CH(中文) |
| book_name | VARCHAR | 200 | 圖書名稱 | |
| book_author | VARCHAR | 200 | 作者名稱 | |
| book_publisher | VARCHAR | 200 | 出版商 | |
| book_pub_date | DATE | | 出版日期 | 格式：YYYY-MM-DD |
| book_create_date | DATE | | 創建日期 | 系統自動生成 |
| book_status | VARCHAR | 2 | 圖書狀態 | 例如：01(可借閱), 02(已借出) |
| book_borrower_ID | VARCHAR | 10 | 借閱者ID | 僅當book_status為已借出時有值 |
| borrow_date | DATE | | 借閱日期 | 僅當book_status為已借出時有值 |

### 借閱日誌表 (BORROW_LOG)

| 欄位名稱 | 欄位類型 | 長度 | 描述 | 備註 |
|---------|---------|------|------|-----|
| log_id | BIGINT | | 日誌ID | 主鍵，自增 |
| book_ISBN | VARCHAR | 30 | 圖書ISBN編號 | 外鍵，關聯BOOK_INFO表 |
| borrower_ID | VARCHAR | 10 | 借閱者ID | |
| borrow_action | VARCHAR | 2 | 借閱動作 | 例如：01(借出), 02(歸還) |
| action_date | TIMESTAMP | | 操作日期時間 | 系統自動生成 |

## 系統狀態和錯誤代碼

### 圖書狀態 (BookStatus)

| 代碼 | 描述 |
|-----|-----|
| 01 | 可借閱 |
| 02 | 已借出 |

### 借閱動作 (BorrowAction)

| 代碼 | 描述 |
|-----|-----|
| 01 | 借出 |
| 02 | 歸還 |

### 響應代碼 (ResponseCode)

| 代碼 | 描述 |
|-----|-----|
| 00 | 成功 |
| 99 | 失敗 |

### 錯誤代碼 (ErrorCode)

| 代碼 | 描述 |
|-----|-----|
| E001 | 圖書不存在 |
| E002 | 圖書已借出 |
| E003 | 圖書未借出 |
| E004 | 借閱者ID不匹配 |
| E005 | 系統錯誤 |

## 系統配置

### 配置文件說明

項目包含兩個主要配置文件：

1. **application.properties**：主要配置文件，包含數據庫配置和通用設置
2. **application-local.properties**：本地開發環境特定配置

### 數據庫初始化

系統使用`data.sql`文件在啟動時初始化數據庫，創建必要的表和測試數據。

### 日誌配置

系統使用`logback.xml`配置日誌輸出格式和級別。

## 開發指南

### 環境要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本
- 開發IDE (推薦IntelliJ IDEA或Eclipse)

### 構建和運行

```bash
# 構建項目
mvn clean package

# 運行項目
java -jar target/cbe-svc-library-manage-1.0.0.jar
```

### 訪問API文檔

啟動應用後，可通過以下URL訪問Swagger UI API文檔：

```
http://localhost:8080/swagger-ui/index.html
```

### 測試覆蓋率報告

運行測試後，可在`target/site/jacoco/index.html`查看測試覆蓋率報告。

## 未來優化方向

1. 添加用戶認證和授權功能
2. 實現圖書推薦系統
3. 添加批量導入/導出功能
4. 集成電子書功能
5. 實現預約和排隊功能
6. 添加圖書評論和評分功能
7. 提供多語言支持
8. 整合外部圖書數據源
9. 實現移動端API
10. 添加詳細的審計日誌

## 技術債

1. 增強錯誤處理和日誌記錄機制
2. 添加更完整的單元測試和集成測試
3. 優化查詢性能，添加適當的索引
4. 實現緩存機制提高性能
5. 完善API文檔和使用指南
