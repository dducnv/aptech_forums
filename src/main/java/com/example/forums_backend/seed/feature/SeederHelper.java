package com.example.forums_backend.seed.feature;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <div>
 *     Đọc dữ liệu từ file excel, insert vào database.
 *     Tạm thời dev phải hỗ trợ test sinh lại dữ liệu bằng cách chạy lại lệnh trên môi trường test.
 *     Version tiếp theo sẽ hỗ trợ tester tự sinh lại thông qua url và link file.
 *     <br><br>
 *     <code>
 *         ./mvnw spring-boot:run -Dspring-boot.run.arguments="file_01.xlsx  file_02.xlsx file_03.xlsx"
 *     </code>
 *     <br><br>
 *     Khi viết seeder cho một chức năng mới, tạo một implement của interface, viết lại hàm readManuallyFromResources
 *     để đọc dữ liệu từ file excel, đặt giá trị cho @Component và sửa lại tên @Qualifier trong SeederPreparation.
 *     <br><br>
 *     Trong một file excel có thể chia thành nhiều sheet, mỗi sheet chứa dữ liệu của một bảng, được sắp xếp theo thứ tự.
 *     Giá trị của các bảng được save theo thứ tự này, vì vậy những bảng có quan hệ khóa ngoại, khóa chính cần sắp xếp để
 *     những bảng reference được lưu trước, cơ bản là quan hệ sản phẩm và danh mục, danh mục sản phẩm cần đặt trong sheet đầu tiên,
 *     sau đó mới sản phẩm
 * </div>
 */
//@Profile("test")
@Component
public interface SeederHelper {
    void readManuallyFromResources() throws IOException;
    void readManuallyFromResources1() throws IOException;
}
