package report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import database.DBConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

public class ReportGenerator {

    public static void generateRentalReport(){

        try{

            java.sql.Connection con = database.DBConnection.getConnection();

            String jrxmlPath = "src/report/RentalReport.jrxml";

            net.sf.jasperreports.engine.JasperReport jr =
                    net.sf.jasperreports.engine.JasperCompileManager.compileReport(jrxmlPath);

            java.util.Map<String, Object> parameters = new java.util.HashMap<>();

            net.sf.jasperreports.engine.JasperPrint jp =
                    net.sf.jasperreports.engine.JasperFillManager.fillReport(
                            jr,
                            parameters,
                            con
                    );

            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}