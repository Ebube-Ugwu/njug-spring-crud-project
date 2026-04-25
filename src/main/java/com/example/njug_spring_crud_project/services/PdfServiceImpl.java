package com.example.njug_spring_crud_project.services;

import com.example.njug_spring_crud_project.dtos.EmployeeResponseDto;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public void exportToPDF(List<EmployeeResponseDto> employees,
                            OutputStream out) {
        var document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);

        document.open();

        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        var title = new Paragraph("List Of Employees", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        var table = new PdfPTable(7);
        table.setWidthPercentage(100);
        String[] headers = {"First Name", "Last Name", "Email", "Dept", "Salary", "Joined", "Active"};
        var headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        for (var header : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(header,
                    headerFont ));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        var rowFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
        for (var employee : employees) {
            table.addCell(new Phrase(employee.firstName(), rowFont));
            table.addCell(new Phrase(employee.lastName(), rowFont));
            table.addCell(new Phrase(employee.email(), rowFont));
            table.addCell(new Phrase(employee.department(), rowFont));
            table.addCell(new Phrase(String.valueOf(employee.salary()), rowFont));
            table.addCell(new Phrase(String.valueOf(employee.dateOfJoining()), rowFont));
            table.addCell(new Phrase(String.valueOf(employee.active()), rowFont));
        }
        document.add(table);

        document.close();
    }
}
