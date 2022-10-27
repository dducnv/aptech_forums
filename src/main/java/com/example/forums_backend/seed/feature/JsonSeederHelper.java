package com.example.forums_backend.seed.feature;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.repository.AccountRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Đọc nội dung file excel, parse kiểu json để tránh việc ép kiểu nhiều trường, giảm việc cho dev.
 */
//@Profile("dev")
@Component("jsonSeeder")
@RequiredArgsConstructor
public class JsonSeederHelper implements SeederHelper {

    final AccountRepository accountRepository;
    private static final String DEFAULT_PATH = "classpath:seed/";
    // Tên file dữ liệu được lấy từ tester và lưu trong thư mục resources/seed
    private static final String FILE_NAME = "account_seed.xlsx";

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))).create();

    @Override
    public void readManuallyFromResources() throws IOException {
        File file = ResourceUtils.getFile(DEFAULT_PATH + FILE_NAME);
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        System.out.println("Feel like reading file is ok.");
        // Có thể đọc nhiều sheet và save vào nhiều bảng.
        // Đọc sheet đầu tiên.
        Sheet sheet = workbook.getSheetAt(0);
        // Tạo danh sách đối tượng để lưu.
        List<Account> accounts = new ArrayList<>();
        boolean hasData = true;
        boolean isColumnName = true;
        // Duyệt từng dòng trong sheet.
        List<String> keys = new ArrayList<>();
        for (Row row : sheet) {
            // xử lý lấy danh mục các trường
            if (isColumnName) {
                for (Cell cell : row) {
                    keys.add(cell.getStringCellValue());
                }
                isColumnName = false;
                continue;
            }
            // Mỗi dòng là một đối tượng cần lưu, tạo mới đối tượng.
            Account obj;
            // Duyệt từng cột để lấy dữ liệu đưa vào đối tượng.
            HashMap<String, Object> jsonMap = new HashMap<>();
            Object idValue = null;
            for (Cell cell : row) {
                Object objValue = null;
                switch (cell.getCellType()) {
                    case NUMERIC:
                        objValue = cell.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        objValue = cell.getBooleanCellValue();
                        break;
                    case _NONE:
                    case BLANK:
                    case ERROR:
                        break;
                    default:
                        objValue = cell.getStringCellValue();
                        break;
                }
                if ("id".equals(keys.get(cell.getColumnIndex()))) {
                    idValue = objValue;
                }
                if (idValue == null) {
                    hasData = false;
                    break;
                }
                jsonMap.put(keys.get(cell.getColumnIndex()), objValue);
            }
            if (!hasData) {
                break;
            }
            String jsonData = gson.toJson(jsonMap);
            System.out.println(jsonData);
            try {
                obj = gson.fromJson(jsonData, Account.class);
                accounts.add(obj);
            } catch (Exception ex) {
                System.err.println("Can't parse information from json.\nError: " + ex.getMessage());
            }
        }
//        System.out.println(gson.toJson(products));
        workbook.close();
        // Lưu thông tin vào database.
        accountRepository.saveAll(accounts);
    }
}
