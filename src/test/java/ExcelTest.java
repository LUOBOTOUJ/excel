import com.vitality.ExcelService;
import org.junit.Test;

public class ExcelTest {
    @Test
    public void func(){

        ExcelService excelService = new ExcelService();
        try {
            excelService.insertByExcel("/Users/egoshiten/Desktop/aaa");
        }catch (Exception e){

        }
    }
}
