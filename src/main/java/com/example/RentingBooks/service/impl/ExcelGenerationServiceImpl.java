package com.example.RentingBooks.service.impl;

import com.example.RentingBooks.projection.BookCategoryProjection;
import com.example.RentingBooks.repo.BookRepo;
import com.example.RentingBooks.service.ExcelGenerationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    private final BookRepo bookRepo;
    private final Integer NO_OF_COL = 8;

     @Override
    public Boolean generateExcel(){
        List<BookCategoryProjection> booklist = bookRepo.findAllData();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Book Details");
        Integer rowCount = 0;
        sheet.createFreezePane(0, 1, 0, 1);
        Row row = sheet.createRow(rowCount);
        IntStream intStream = IntStream.range(0, NO_OF_COL);
        intStream.forEach(i -> {
            Cell cell = row.createCell(i);
            switch (i) {
                case 0:
                    cell.setCellValue("ID");
                    break;
                case 1:
                    cell.setCellValue("Book Name");
                    break;
                case 2:
                    cell.setCellValue("No of Pages");
                    break;
                case 3:
                    cell.setCellValue("ISBN");
                    break;
                case 4:
                    cell.setCellValue("Rating");
                    break;
                case 5:
                    cell.setCellValue("Stock Count");
                    break;
                case 6:
                    cell.setCellValue("Published Date");
                    break;
                case 7:
                    cell.setCellValue("Category Name");
                    break;
                default:

            }
        });
        rowCount++;
        AtomicReference<Integer> rowCountForData = new AtomicReference<>(rowCount);
        booklist.forEach(book -> {
            Row dataRow = sheet.createRow(rowCountForData.get());
            IntStream intStreamDat = IntStream.range(0, NO_OF_COL);
            intStreamDat.forEach(i -> {
                Cell cell = dataRow.createCell(i);
                switch (i) {
                    case 0:
                        cell.setCellValue(book.getId());
                        break;
                    case 1:
                        cell.setCellValue(book.getBookName());
                        break;
                    case 2:
                        cell.setCellValue(book.getNoOfPages());
                        break;
                    case 3:
                        cell.setCellValue(book.getISBN());
                        break;
                    case 4:
                        cell.setCellValue(book.getRating());
                        break;
                    case 5:
                        cell.setCellValue(book.getStockCount());
                        break;
                    case 6:
                        cell.setCellValue(book.getPublishedDate());
                        break;
                    case 7:
                        cell.setCellValue(book.getCategoryName());
                        break;
                    default:

                }
            });
            rowCountForData.getAndSet(rowCountForData.get() + 1);
        });
        rowCount = rowCountForData.get();
        try (FileOutputStream outputStream = new FileOutputStream("excel/output.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
         return true;
    }
}