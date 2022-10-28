package com.example.forums_backend.seed;

import com.example.forums_backend.seed.feature.SeederHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * <div>
 *     Chỉ chạy trong môi trường test, không được phép sử dụng trong môi trường production.
 *     Class chỉ dùng trong môi trường test, dùng để load dữ liệu seed cho một test case. Hàm run được chạy mỗi khi project bắt đầu.
 *     Dùng để đọc dữ liệu từ file excel và insert vào database. File excel được đặt trong thư mục resources/seed của project spring.
 *     Trong file dữ liệu được chia thành các hàng và cột, cột là các trường trong database tương ứng.
 *     Tên file được truyền vào là một tham số khi chạy project, ví dụ:
 * </div>
 * <code>
 *     ./mvnw spring-boot:run -Dspring-boot.run.arguments="file_01.xlsx  file_02.xlsx file_03.xlsx"
 * </code>
 * <p>
 *     Có thể chạy và load dữ liệu từ nhiều file, các file cách nhau bởi dấu cách.
 * </p>
 */
//@Profile("test")
//@Component
public class SeederPreparation implements CommandLineRunner {
    /**
     * Chuyển qualifier theo tên @Component mới tạo.
     * */
    @Autowired
    @Qualifier("jsonSeeder")
    SeederHelper seederHelper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Prepare seed data...");
        seederHelper.readManuallyFromResources();
        System.out.println("Action success!");
    }
}
